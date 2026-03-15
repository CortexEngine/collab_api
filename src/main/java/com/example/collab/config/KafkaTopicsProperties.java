package com.example.collab.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.kafka")
public class KafkaTopicsProperties {

    private final Topics topics = new Topics();

    public Topics getTopics() {

        return topics;

    }

    public static class Topics {

        private String collaboratorCreated;
        private String collaboratorUpdated;
        private String collaboratorDeleted;
        private String departmentCreated;
        private String departmentUpdated;
        private String departmentDeleted;

        public String getCollaboratorCreated() {

            return collaboratorCreated;

        }

        public void setCollaboratorCreated(String collaboratorCreated) {

            this.collaboratorCreated = collaboratorCreated;

        }

        public String getCollaboratorUpdated() {

            return collaboratorUpdated;

        }

        public void setCollaboratorUpdated(String collaboratorUpdated) {

            this.collaboratorUpdated = collaboratorUpdated;

        }

        public String getCollaboratorDeleted() {

            return collaboratorDeleted;

        }

        public void setCollaboratorDeleted(String collaboratorDeleted) {

            this.collaboratorDeleted = collaboratorDeleted;

        }

        public String getDepartmentCreated() {

            return departmentCreated;

        }

        public void setDepartmentCreated(String departmentCreated) {

            this.departmentCreated = departmentCreated;

        }

        public String getDepartmentUpdated() {

            return departmentUpdated;

        }

        public void setDepartmentUpdated(String departmentUpdated) {

            this.departmentUpdated = departmentUpdated;

        }

        public String getDepartmentDeleted() {

            return departmentDeleted;

        }

        public void setDepartmentDeleted(String departmentDeleted) {

            this.departmentDeleted = departmentDeleted;
            
        }
    }
}