package com.example.collab.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.collab.domain.model.OutboxEvent;
import com.example.collab.repository.OutboxEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("OutboxService Tests")
class OutboxServiceTest {

    @Mock
    private OutboxEventRepository outboxEventRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OutboxService outboxService;

    @Test
    @DisplayName("Should save outbox event with serialized payload")
    void shouldSaveOutboxEventWithSerializedPayload() throws Exception {
        Object payload = new Object();

        when(objectMapper.writeValueAsString(payload)).thenReturn("{\"name\":\"event\"}");
        when(outboxEventRepository.save(any(OutboxEvent.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OutboxEvent result = outboxService.saveEvent(
            "DEPARTMENT",
            "10",
            "DEPARTMENT_CREATED",
            "collab.department.created",
            payload
        );

        ArgumentCaptor<OutboxEvent> eventCaptor = ArgumentCaptor.forClass(OutboxEvent.class);
        verify(outboxEventRepository).save(eventCaptor.capture());

        OutboxEvent savedEvent = eventCaptor.getValue();
        assertEquals("DEPARTMENT", savedEvent.getAggregateType());
        assertEquals("10", savedEvent.getAggregateId());
        assertEquals("DEPARTMENT_CREATED", savedEvent.getEventType());
        assertEquals("collab.department.created", savedEvent.getTopic());
        assertEquals("{\"name\":\"event\"}", savedEvent.getPayload());
        assertEquals(savedEvent, result);
    }

    @Test
    @DisplayName("Should throw when payload serialization fails")
    void shouldThrowWhenPayloadSerializationFails() throws Exception {
        Object payload = new Object();

        when(objectMapper.writeValueAsString(payload))
            .thenThrow(new JsonProcessingException("serialization error") { });

        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> outboxService.saveEvent("DEPARTMENT", "10", "DEPARTMENT_CREATED", "topic", payload)
        );

        assertEquals("Erro ao serializar payload do outbox", exception.getMessage());
        verify(outboxEventRepository, never()).save(any(OutboxEvent.class));
    }
}
