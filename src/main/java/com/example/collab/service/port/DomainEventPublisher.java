package com.example.collab.service.port;

public interface DomainEventPublisher {

    void publish(String aggregateType, String aggregateId, String eventType, Object payload);
    
}