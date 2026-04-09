package com.example.collab.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.collab.domain.model.OutboxEvent;
import com.example.collab.domain.valueobject.OutboxStatus;
import com.example.collab.repository.OutboxEventRepository;

@Service
public class OutboxRelayService {

    private final OutboxEventRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TransactionTemplate tx;
    private final int maxAttempts;

    public OutboxRelayService(OutboxEventRepository repository, KafkaTemplate<String, String> kafkaTemplate,TransactionTemplate tx, @Value("${app.kafka.outbox.max-attempts:5}") int maxAttempts) {
        
        this.repository = repository;

        this.kafkaTemplate = kafkaTemplate;

        this.tx = tx;

        this.maxAttempts = maxAttempts;

    }

    @Scheduled(fixedDelayString = "${app.kafka.outbox.fixed-delay-ms:5000}")
    public void publishPendingEvents() {

        List<OutboxEvent> batch = tx.execute(status -> {

            List<OutboxEvent> events = repository.findByStatusInAndAttemptsLessThanOrderByIdAsc(

                    List.of(OutboxStatus.PENDING, OutboxStatus.FAILED), maxAttempts, PageRequest.of(0, 100));

            Instant now = Instant.now();

            events.forEach(e -> {

                e.setStatus(OutboxStatus.PROCESSING);

                e.setProcessingStartedAt(now);

            });

            repository.saveAll(events);

            return events.stream().map(this::copy).toList();

        });

        if (batch == null || batch.isEmpty()) return;

        for (OutboxEvent event : batch) {

            try {

                kafkaTemplate.send(event.getTopic(), event.getAggregateId(), event.getPayload()).get(10, TimeUnit.SECONDS);

                tx.executeWithoutResult(s -> {

                    OutboxEvent db = repository.findById(event.getId()).orElseThrow();

                    db.setStatus(OutboxStatus.SENT);

                    db.setPublishedAt(Instant.now());

                    db.setProcessingStartedAt(null);

                    db.setLastError(null);

                });

            } catch (Exception ex) {

                tx.executeWithoutResult(s -> {

                    OutboxEvent db = repository.findById(event.getId()).orElseThrow();

                    db.setAttempts(db.getAttempts() + 1);

                    db.setStatus(OutboxStatus.FAILED);

                    db.setProcessingStartedAt(null);

                    db.setLastError(ex.getMessage());

                });

            }

        }

    }

    @Scheduled(fixedDelayString = "${app.kafka.outbox.recover-processing-ms:60000}")
    public void recoverStuckProcessing() {

        tx.executeWithoutResult(s -> releaseStuckProcessing( Instant.now().minus(2, ChronoUnit.MINUTES)));

    }

        public int releaseStuckProcessing(Instant threshold) {

        List<OutboxEvent> stuck = repository.findByStatusAndProcessingStartedAtBefore(OutboxStatus.PROCESSING, threshold);

        for (OutboxEvent event : stuck) {

            event.setStatus(OutboxStatus.FAILED);

            event.setProcessingStartedAt(null);

            event.setLastError("Recovering stuck PROCESSING event");

        }

        repository.saveAll(stuck);

        return stuck.size();

    }

    private OutboxEvent copy(OutboxEvent e) {

        OutboxEvent c = new OutboxEvent();

        c.setId(e.getId());

        c.setTopic(e.getTopic());

        c.setAggregateId(e.getAggregateId());

        c.setPayload(e.getPayload());

        return c;

    }
    
}