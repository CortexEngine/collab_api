package com.example.collab.service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public OutboxRelayService(OutboxEventRepository outboxEventRepository, KafkaTemplate<String, String> kafkaTemplate) {

        this.outboxEventRepository = outboxEventRepository;

        this.kafkaTemplate = kafkaTemplate;
        
    }

    @Scheduled(fixedDelayString = "${app.kafka.outbox.fixed-delay-ms:5000}")
    @Transactional
    public void publishPendingEvents() {

        List<OutboxEvent> pendingEvents = outboxEventRepository.findTop100ByStatusOrderByIdAsc(OutboxStatus.PENDING);

        for (OutboxEvent event : pendingEvents) {

            try {

                kafkaTemplate.send(event.getTopic(), event.getAggregateId(), event.getPayload()).get(10, TimeUnit.SECONDS);

                event.setStatus(OutboxStatus.SENT);

                event.setPublishedAt(Instant.now());

                event.setLastError(null);

            } catch (Exception ex) {

                event.setStatus(OutboxStatus.FAILED);

                event.setAttempts(event.getAttempts() + 1);

                event.setLastError(ex.getMessage());

            }

        }

        outboxEventRepository.saveAll(pendingEvents);

    }
}