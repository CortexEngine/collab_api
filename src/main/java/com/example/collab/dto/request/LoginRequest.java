package com.example.collab.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank 
        String username,

        @NotBlank 
        String password

) {
}
