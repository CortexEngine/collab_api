package com.example.collab.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.collab.domain.model.OutboxEvent;
import com.example.collab.domain.valueobject.OutboxStatus;
import com.example.collab.repository.OutboxEventRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("OutboxRelayService Tests")
class OutboxRelayServiceTest {

    @Mock
    private OutboxEventRepository repository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private TransactionTemplate tx;

    private OutboxRelayService outboxRelayService;

    @BeforeEach
    void setUp() {
        outboxRelayService = new OutboxRelayService(repository, kafkaTemplate, tx, 5);
    }

    @Test
    @DisplayName("Should mark event as SENT when kafka publish succeeds")
    void shouldMarkEventAsSentWhenKafkaPublishSucceeds() throws Exception {
        OutboxEvent pendingEvent = new OutboxEvent();
        pendingEvent.setId(1L);
        pendingEvent.setStatus(OutboxStatus.PENDING);
        pendingEvent.setAttempts(0);
        pendingEvent.setTopic("collab.department.created");
        pendingEvent.setAggregateId("10");
        pendingEvent.setPayload("{\"id\":10}");

        when(tx.execute(any())).thenAnswer(invocation -> {
            Object result = ((org.springframework.transaction.support.TransactionCallback<?>) invocation.getArgument(0))
                    .doInTransaction(null);
            return result;
        });

        when(repository.findByStatusInAndAttemptsLessThanOrderByIdAsc(
                List.of(OutboxStatus.PENDING, OutboxStatus.FAILED), 5, PageRequest.of(0, 100)))
            .thenReturn(List.of(pendingEvent));

        when(kafkaTemplate.send("collab.department.created", "10", "{\"id\":10}"))
            .thenReturn(CompletableFuture.completedFuture(null));

        when(repository.findById(1L)).thenReturn(Optional.of(pendingEvent));

        outboxRelayService.publishPendingEvents();

        assertEquals(OutboxStatus.SENT, pendingEvent.getStatus());
        assertNotNull(pendingEvent.getPublishedAt());
        assertNull(pendingEvent.getLastError());
        assertNull(pendingEvent.getProcessingStartedAt());
    }

    @Test
    @DisplayName("Should mark event as FAILED and increment attempts when kafka publish fails")
    void shouldMarkEventAsFailedWhenKafkaPublishFails() throws Exception {
        OutboxEvent pendingEvent = new OutboxEvent();
        pendingEvent.setId(1L);
        pendingEvent.setStatus(OutboxStatus.PENDING);
        pendingEvent.setAttempts(0);
        pendingEvent.setTopic("collab.department.created");
        pendingEvent.setAggregateId("10");
        pendingEvent.setPayload("{\"id\":10}");

        when(tx.execute(any())).thenAnswer(invocation -> {
            Object result = ((org.springframework.transaction.support.TransactionCallback<?>) invocation.getArgument(0))
                    .doInTransaction(null);
            return result;
        });

        when(repository.findByStatusInAndAttemptsLessThanOrderByIdAsc(
                List.of(OutboxStatus.PENDING, OutboxStatus.FAILED), 5, PageRequest.of(0, 100)))
            .thenReturn(List.of(pendingEvent));

        when(kafkaTemplate.send("collab.department.created", "10", "{\"id\":10}"))
            .thenReturn(CompletableFuture.failedFuture(new RuntimeException("broker unavailable")));

        when(repository.findById(1L)).thenReturn(Optional.of(pendingEvent));

        outboxRelayService.publishPendingEvents();

        assertEquals(OutboxStatus.FAILED, pendingEvent.getStatus());
        assertEquals(1, pendingEvent.getAttempts());
        assertNotNull(pendingEvent.getLastError());
        assertTrue(pendingEvent.getLastError().contains("broker unavailable"));
    }

    @Test
    @DisplayName("Should not publish when there are no pending events")
    void shouldNotPublishWhenThereAreNoPendingEvents() {
        when(tx.execute(any())).thenAnswer(invocation -> {
            Object result = ((org.springframework.transaction.support.TransactionCallback<?>) invocation.getArgument(0))
                    .doInTransaction(null);
            return result;
        });

        when(repository.findByStatusInAndAttemptsLessThanOrderByIdAsc(
                List.of(OutboxStatus.PENDING, OutboxStatus.FAILED), 5, PageRequest.of(0, 100)))
            .thenReturn(List.of());

        outboxRelayService.publishPendingEvents();

        verify(kafkaTemplate, never()).send(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Should recover stuck PROCESSING events after 2 minutes")
    void shouldRecoverStuckProcessingEvents() {
        OutboxEvent stuckEvent = new OutboxEvent();
        stuckEvent.setId(1L);
        stuckEvent.setStatus(OutboxStatus.PROCESSING);
        stuckEvent.setAttempts(2);
        stuckEvent.setProcessingStartedAt(Instant.now().minusSeconds(125));

        when(repository.findByStatusAndProcessingStartedAtBefore(
                OutboxStatus.PROCESSING, any(Instant.class)))
            .thenReturn(List.of(stuckEvent));

        outboxRelayService.releaseStuckProcessing(Instant.now().minusSeconds(120));

        assertEquals(OutboxStatus.FAILED, stuckEvent.getStatus());
        assertNull(stuckEvent.getProcessingStartedAt());
        assertTrue(stuckEvent.getLastError().contains("stuck"));
    }
}
