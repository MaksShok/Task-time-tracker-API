package ru.code.tasktracker.dto;

import jakarta.validation.constraints.NotNull;
import ru.code.tasktracker.models.task.TaskStatus;

public class UpdateTaskStatusRequestInfo
{
    @NotNull(message = "Task status should not be null")
    private TaskStatus status;

    public UpdateTaskStatusRequestInfo(TaskStatus status)
    {
        this.status = status;
    }

    public TaskStatus getStatus() {
        return status;
    }
}

