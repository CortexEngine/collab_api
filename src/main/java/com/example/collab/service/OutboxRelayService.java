package com.example.collab.service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.collab.domain.model.OutboxEvent;
import com.example.collab.domain.valueobject.OutboxStatus;
import com.example.collab.repository.OutboxEventRepository;

@Service
public class OutboxRelayService {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final int maxAttempts;

    public OutboxRelayService(OutboxEventRepository outboxEventRepository, KafkaTemplate<String, String> kafkaTemplate, @Value("${app.kafka.outbox.max-attempts:5}") int maxAttempts) 
    {
        this.outboxEventRepository = outboxEventRepository;

        this.kafkaTemplate = kafkaTemplate;

        this.maxAttempts = maxAttempts;

    }

    @Scheduled(fixedDelayString = "${app.kafka.outbox.fixed-delay-ms:5000}")
    @Transactional
    public void publishPendingEvents() {

        List<OutboxEvent> events = outboxEventRepository.findTop100ByStatusInAndAttemptsLessThanOrderByIdAsc( List.of(OutboxStatus.PENDING, OutboxStatus.FAILED), maxAttempts);

        for (OutboxEvent event : events) {

            try {

                kafkaTemplate.send(event.getTopic(), event.getAggregateId(), event.getPayload()).get(10, TimeUnit.SECONDS);

                event.setStatus(OutboxStatus.SENT);

                event.setPublishedAt(Instant.now());

                event.setLastError(null);

            } catch (Exception ex) {

                event.setAttempts(event.getAttempts() + 1);

                event.setLastError(ex.getMessage());

                event.setStatus(OutboxStatus.FAILED);

            }

        }

        outboxEventRepository.saveAll(events);
        
    }
}