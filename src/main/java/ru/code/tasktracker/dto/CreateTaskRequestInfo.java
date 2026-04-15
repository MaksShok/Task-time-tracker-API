package ru.code.tasktracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTaskRequestInfo
{
    @NotBlank(message = "The task name should not be empty.")
    public final String name;

    @Size(max = 255)
    public final String description;

    public CreateTaskRequestInfo(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
}
