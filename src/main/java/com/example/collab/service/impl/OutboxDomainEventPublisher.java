package com.example.collab.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import com.example.collab.config.KafkaTopicResolver;
import com.example.collab.service.OutboxService;
import com.example.collab.service.port.DomainEventPublisher;

@Service
public class OutboxDomainEventPublisher implements DomainEventPublisher {

    private final OutboxService outboxService;

    private final KafkaTopicResolver topicResolver;

    public OutboxDomainEventPublisher(OutboxService outboxService, KafkaTopicResolver topicResolver) {

        this.outboxService = outboxService;

        this.topicResolver = topicResolver;

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void publish(String aggregateType, String aggregateId, String eventType, Object payload) {

        String topic = topicResolver.resolve(eventType);

        outboxService.saveEvent(aggregateType, aggregateId, eventType, topic, payload);

    }

}
