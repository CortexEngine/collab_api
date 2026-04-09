package com.example.collab.service;

import org.springframework.stereotype.Service;

import com.example.collab.domain.model.OutboxEvent;
import com.example.collab.repository.OutboxEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OutboxService {

    private final OutboxEventRepository outboxEventRepository;

    private final ObjectMapper objectMapper;

    public OutboxService(OutboxEventRepository outboxEventRepository, ObjectMapper objectMapper) {

        this.outboxEventRepository = outboxEventRepository;

        this.objectMapper = objectMapper;

    }

    public OutboxEvent saveEvent(String aggregateType, String aggregateId, String eventType, String topic, Object payload) {

        OutboxEvent event = new OutboxEvent();

        event.setAggregateType(aggregateType);

        event.setAggregateId(aggregateId);

        event.setEventType(eventType);

        event.setTopic(topic);

        event.setPayload(toJson(payload));

        return outboxEventRepository.save(event);

    }

    private String toJson(Object payload) {

        try {

            return objectMapper.writeValueAsString(payload);

        } catch (JsonProcessingException exception) {

            throw new IllegalStateException("Erro ao serializar payload do outbox", exception);

        }

    }
    
}