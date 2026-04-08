package com.example.collab.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.collab.config.KafkaTopicResolver;
import com.example.collab.service.OutboxService;

@ExtendWith(MockitoExtension.class)
@DisplayName("OutboxDomainEventPublisher Tests")
class OutboxDomainEventPublisherTest {

    @Mock
    private OutboxService outboxService;

    @Mock
    private KafkaTopicResolver topicResolver;

    @InjectMocks
    private OutboxDomainEventPublisher outboxDomainEventPublisher;

    @Test
    @DisplayName("Should resolve topic and save event in outbox")
    void shouldResolveTopicAndSaveEventInOutbox() {
        Object payload = new Object();

        when(topicResolver.resolve("DEPARTMENT_CREATED")).thenReturn("collab.department.created");

        outboxDomainEventPublisher.publish("DEPARTMENT", "10", "DEPARTMENT_CREATED", payload);

        verify(topicResolver).resolve("DEPARTMENT_CREATED");
        verify(outboxService).saveEvent(
            "DEPARTMENT",
            "10",
            "DEPARTMENT_CREATED",
            "collab.department.created",
            payload
        );
    }

    @Test
    @DisplayName("Should propagate exception when topic is not mapped")
    void shouldPropagateExceptionWhenTopicIsNotMapped() {
        Object payload = new Object();

        when(topicResolver.resolve("UNKNOWN_EVENT"))
            .thenThrow(new IllegalArgumentException("Topic não configurado para eventType: UNKNOWN_EVENT"));

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> outboxDomainEventPublisher.publish("DEPARTMENT", "10", "UNKNOWN_EVENT", payload)
        );

        assertEquals("Topic não configurado para eventType: UNKNOWN_EVENT", exception.getMessage());
        verify(outboxService, never()).saveEvent(anyString(), anyString(), anyString(), anyString(), any());
    }
}
