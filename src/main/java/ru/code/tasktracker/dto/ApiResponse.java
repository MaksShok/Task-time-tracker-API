package ru.code.tasktracker.dto;

import java.time.LocalDateTime;

public class ApiResponse<T>
{
    public T data;
    public String message;
    public LocalDateTime dateTime;

    public ApiResponse(T data, String message)
    {
        this.data = data;
        this.message = message;
        dateTime = LocalDateTime.now();
    }
}
