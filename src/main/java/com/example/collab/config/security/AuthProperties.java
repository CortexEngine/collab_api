package com.example.collab.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.auth")
public record AuthProperties(List<AuthUser> users) {
}
