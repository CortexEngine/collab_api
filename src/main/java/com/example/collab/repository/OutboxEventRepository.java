package com.example.collab.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.example.collab.domain.model.OutboxEvent;
import com.example.collab.domain.valueobject.OutboxStatus;

import jakarta.persistence.LockModeType;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<OutboxEvent> findByStatusInAndAttemptsLessThanOrderByIdAsc(Collection<OutboxStatus> statuses,Integer maxAttempts,Pageable pageable);

    List<OutboxEvent> findByStatusAndProcessingStartedAtBefore(OutboxStatus status,Instant threshold);
    
}
