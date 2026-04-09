package com.example.collab.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.collab.domain.model.OutboxEvent;
import com.example.collab.domain.valueobject.OutboxStatus;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus status);

    List<OutboxEvent> findTop100ByStatusOrderByIdAsc(OutboxStatus status);
    
    List<OutboxEvent> findTop100ByStatusInAndAttemptsLessThanOrderByIdAsc(
            Collection<OutboxStatus> statuses,
            Integer maxAttempts
    );
}
