package com.example.collab.config.security;

import java.util.List;

public record AuthUser(String username, String password, List<String> roles) {
}
