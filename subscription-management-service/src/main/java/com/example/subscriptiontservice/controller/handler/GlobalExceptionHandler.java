package com.example.subscriptiontservice.controller.handler;

import com.example.subscriptiontservice.dto.Message;
import com.example.subscriptiontservice.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Message> handleResourceNotFound(ResourceNotFoundException exception) {
        Message message = new Message(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Message> handleIllegalArgumentException(IllegalArgumentException ex) {
        Message message = new Message(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> handleGeneralException(Exception exception) {
        Message message = new Message("Unexpected error: " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}

