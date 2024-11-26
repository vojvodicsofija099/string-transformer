package com.stringtransformer.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleException(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getAllValidationResults().forEach(violation -> {
            String parameterName = violation.getMethodParameter().getParameterName();
            violation.getResolvableErrors().forEach(e -> errors.put(parameterName, e.getDefaultMessage()));
        });
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(StringTransformerException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ResponseEntity<String> handleBusinessException(StringTransformerException ex) {
        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .contentType(APPLICATION_JSON)
                .body(ex.getMessage());
    }
}
