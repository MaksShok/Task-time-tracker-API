package ru.code.tasktracker.dto;

import jakarta.validation.constraints.NotNull;
import ru.code.tasktracker.models.task.TaskStatus;

public class UpdateTaskStatusRequestInfo
{
    @NotNull(message = "Task status should not be null")
    public final TaskStatus status;

    public UpdateTaskStatusRequestInfo(TaskStatus status)
    {
        this.status = status;
    }
}

