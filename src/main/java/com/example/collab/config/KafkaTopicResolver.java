package com.example.collab.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class KafkaTopicResolver {

    private final Map<String, String> topicByEventType;

    public KafkaTopicResolver(KafkaTopicsProperties props) {
        this.topicByEventType = buildTopicMap(props);
        validateTopics();
    }

    private Map<String, String> buildTopicMap(KafkaTopicsProperties props) {

        Map<String, String> map = new HashMap<>();

        map.put("COLLABORATOR_CREATED", props.getTopics().getCollaboratorCreated());

        map.put("COLLABORATOR_UPDATED", props.getTopics().getCollaboratorUpdated());

        map.put("COLLABORATOR_DELETED", props.getTopics().getCollaboratorDeleted());

        map.put("DEPARTMENT_CREATED", props.getTopics().getDepartmentCreated());

        map.put("DEPARTMENT_UPDATED", props.getTopics().getDepartmentUpdated());

        map.put("DEPARTMENT_DELETED", props.getTopics().getDepartmentDeleted());

        return map;

    }

    private void validateTopics() {

        Map<String, String> missing = new HashMap<>();

        topicByEventType.forEach((eventType, topic) -> {

            if (topic == null || topic.isBlank()) {

                missing.put(eventType, "não configurado");

            }

        });

        if (!missing.isEmpty()) {

            String errorMsg = "Tópicos Kafka obrigatórios não configurados:\n" + 
            missing.entrySet().stream().map(e -> "  - " + e.getKey() + ": " + e.getValue()).reduce((a, b) -> a + "\n" + b).orElse("");

            throw new IllegalStateException(errorMsg);

        }

    }

    public String resolve(String eventType) {

        String topic = topicByEventType.get(eventType);

        if (topic == null) {

            throw new IllegalArgumentException("EventType não mapeado: " + eventType);

        }

        return topic;

    }
    
}