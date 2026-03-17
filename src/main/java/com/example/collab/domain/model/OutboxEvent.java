package com.example.collab.domain.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID eventId;

    private String aggregateType;

    private String aggregateId;

    private String eventType;

    private String topic;

    private String payload;

    private OutboxStatus status;

    private Integer attempts = 0;

    private Instant createdAt;

    private Instant publishedAt;

    private String lastError;

    @PrePersist
    public void prePersist() {

        if (eventId == null) eventId = UUID.randomUUID();

        if (status == null) status = OutboxStatus.PENDING;

        if (attempts == null) attempts = 0;

        if (createdAt == null) createdAt = Instant.now();

    }

}