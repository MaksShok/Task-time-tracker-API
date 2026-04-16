package ru.code.tasktracker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.code.tasktracker.dto.ApiResponse;

public abstract class BaseController
{
    protected abstract Logger getLogger();

    protected <T> ResponseEntity<ApiResponse<T>> toResponse(
            HttpStatus status,
            T data,
            String message)
    {
        var log = getLogger();
        log.info(message);

        var responseObj = new ApiResponse<>(data, message);
        return ResponseEntity.status(status).body(responseObj);
    }
}
