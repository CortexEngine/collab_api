package com.example.collab.config;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class KafkaTopicResolver {

    private final Map<String, String> topicByEventType;

    public KafkaTopicResolver(KafkaTopicsProperties props) {

        this.topicByEventType = Map.of(

            "COLLABORATOR_CREATED", props.getTopics().getCollaboratorCreated(),

            "COLLABORATOR_UPDATED", props.getTopics().getCollaboratorUpdated(),

            "COLLABORATOR_DELETED", props.getTopics().getCollaboratorDeleted(),

            "DEPARTMENT_CREATED", props.getTopics().getDepartmentCreated(),

            "DEPARTMENT_UPDATED", props.getTopics().getDepartmentUpdated(),

            "DEPARTMENT_DELETED", props.getTopics().getDepartmentDeleted()

        );

    }

    public String resolve(String eventType) {

        String topic = topicByEventType.get(eventType);

        if (topic == null || topic.isBlank()) {

            throw new IllegalArgumentException("Topic não configurado para eventType: " + eventType);

        }

        return topic;

    }
    
}