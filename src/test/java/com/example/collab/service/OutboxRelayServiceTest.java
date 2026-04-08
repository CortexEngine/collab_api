package com.example.collab.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import com.example.collab.domain.model.OutboxEvent;
import com.example.collab.domain.valueobject.OutboxStatus;
import com.example.collab.repository.OutboxEventRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("OutboxRelayService Tests")
class OutboxRelayServiceTest {

    @Mock
    private OutboxEventRepository outboxEventRepository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private OutboxRelayService outboxRelayService;

    @Test
    @DisplayName("Should mark event as SENT when kafka publish succeeds")
    void shouldMarkEventAsSentWhenKafkaPublishSucceeds() {
        OutboxEvent pendingEvent = new OutboxEvent();
        pendingEvent.setStatus(OutboxStatus.PENDING);
        pendingEvent.setAttempts(0);
        pendingEvent.setTopic("collab.department.created");
        pendingEvent.setAggregateId("10");
        pendingEvent.setPayload("{\"id\":10}");

        when(outboxEventRepository.findTop100ByStatusOrderByIdAsc(OutboxStatus.PENDING))
            .thenReturn(List.of(pendingEvent));
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
            .thenReturn(CompletableFuture.completedFuture(null));

        outboxRelayService.publishPendingEvents();

        assertEquals(OutboxStatus.SENT, pendingEvent.getStatus());
        assertNotNull(pendingEvent.getPublishedAt());
        assertNull(pendingEvent.getLastError());
        verify(outboxEventRepository).saveAll(List.of(pendingEvent));
    }

    @Test
    @DisplayName("Should mark event as FAILED and increment attempts when kafka publish fails")
    void shouldMarkEventAsFailedWhenKafkaPublishFails() {
        OutboxEvent pendingEvent = new OutboxEvent();
        pendingEvent.setStatus(OutboxStatus.PENDING);
        pendingEvent.setAttempts(0);
        pendingEvent.setTopic("collab.department.created");
        pendingEvent.setAggregateId("10");
        pendingEvent.setPayload("{\"id\":10}");

        when(outboxEventRepository.findTop100ByStatusOrderByIdAsc(OutboxStatus.PENDING))
            .thenReturn(List.of(pendingEvent));
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
            .thenReturn(CompletableFuture.failedFuture(new RuntimeException("broker unavailable")));

        outboxRelayService.publishPendingEvents();

        assertEquals(OutboxStatus.FAILED, pendingEvent.getStatus());
        assertEquals(1, pendingEvent.getAttempts());
        assertNotNull(pendingEvent.getLastError());
        verify(outboxEventRepository).saveAll(List.of(pendingEvent));
    }

    @Test
    @DisplayName("Should not publish when there are no pending events")
    void shouldNotPublishWhenThereAreNoPendingEvents() {
        when(outboxEventRepository.findTop100ByStatusOrderByIdAsc(OutboxStatus.PENDING))
            .thenReturn(List.of());

        outboxRelayService.publishPendingEvents();

        verify(kafkaTemplate, never()).send(anyString(), anyString(), anyString());
        verify(outboxEventRepository).saveAll(List.of());
    }
}
