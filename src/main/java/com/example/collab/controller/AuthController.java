package com.example.collab.controller;

import com.example.collab.config.security.AuthProperties;
import com.example.collab.config.security.JwtService;
import com.example.collab.dto.request.LoginRequest;
import com.example.collab.dto.response.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthProperties authProperties;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthProperties authProperties, JwtService jwtService, PasswordEncoder passwordEncoder) {

        this.authProperties = authProperties;

        this.jwtService = jwtService;

        this.passwordEncoder = passwordEncoder;

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {

        var user = authProperties.users().stream()
                .filter(u -> u.username().equals(request.username()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid username or password"
                ));

        if (!passwordEncoder.matches(request.password(), user.password())) {

            throw new ResponseStatusException(

                    HttpStatus.UNAUTHORIZED, "Invalid username or password"

            );

        }

        String token = jwtService.generateToken(user.username(), user.roles());

        return ResponseEntity.ok(new LoginResponse(token, "Bearer", user.roles()));

    }
    
}
