package com.example.collab.exception.handler;

import com.example.collab.exception.business.*;
import com.example.collab.exception.domain.*;
import com.example.collab.exception.resource.*;

import com.example.collab.exception.dto.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import jakarta.validation.ConstraintViolationException;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // HTTP 404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, WebRequest req) {

        var body = ErrorResponse.of(HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage(), path(req));

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);

    }

    // HTTP 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, WebRequest req) {

        var body = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "Bad Request", ex.getMessage(), path(req));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

    }

    // HTTP 409
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex, WebRequest req) {

        var body = ErrorResponse.of(HttpStatus.CONFLICT.value(), "Conflict", ex.getMessage(), path(req));

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);

    }

    // HTTP 422
    @ExceptionHandler({ UnprocessableEntityException.class, InvalidDocumentException.class })
    public ResponseEntity<ErrorResponse> handleUnprocessable(RuntimeException ex, WebRequest req) {

        var body = ErrorResponse.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Unprocessable Entity", ex.getMessage(),
                path(req));

        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);

    }

    // HTTP 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, WebRequest req) {

        Map<String, String> details = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err -> details.put(err.getField(), err.getDefaultMessage()));

        var body = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "Validation Error", ex.getMessage(), path(req),
                details);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

    }

    // HTTP 500
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, WebRequest req) {

        var body = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", ex.getMessage(),
                path(req));

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    //Validação de parametros de caminho e requisição
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest req) {
        
        Map<String, String> details = new LinkedHashMap<>();
        
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            details.put(field, message);
        });
        
        var body = ErrorResponse.of(
            HttpStatus.BAD_REQUEST.value(),
            "Validation Error",
            "Invalid path variable or request parameter",
            path(req),
            details
        );
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private String path(WebRequest req) {

        String desc = req.getDescription(false);

        int idx = desc.indexOf("uri=");

        return idx >= 0 ? desc.substring(idx + 4) : desc;

    }
}
