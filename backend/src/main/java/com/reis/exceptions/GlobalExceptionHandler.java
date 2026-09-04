package com.reis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> tratarNaoEncontrado(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task não encontrada.");
    }
}
