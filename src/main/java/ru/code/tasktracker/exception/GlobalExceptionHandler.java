package ru.code.tasktracker.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAnyException(Exception e)
    {
        log.error("Handle exception ", e);
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(exception = {
            IllegalArgumentException.class,
            ValidationFailedException.class})
    public ResponseEntity<String> handleInvalidRequestException(Exception e)
    {
        log.warn("Invalid request: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<String> handleDataProcessingException(Exception e)
    {
        log.warn("Processing error: {}", e.getMessage());
        return ResponseEntity.status(500).body(e.getMessage());
    }

}
