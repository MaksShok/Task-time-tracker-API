package ru.code.tasktracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class CreateTimeRecordRequestInfo
{
    @NotNull(message = "Employee ID must not be null")
    @Positive(message = "Employee ID must be positive")
    private long employeeId;

    @NotNull(message = "Task ID must not be null")
    @Positive(message = "Task ID must be positive")
    private long taskId;

    @NotNull(message = "Start time must not be null")
    private LocalDateTime startTime;

    @NotNull(message = "End time must not be null")
    private LocalDateTime endTime;

    @Size(max = 500)
    private String workDescription;

    public CreateTimeRecordRequestInfo(
            long employeeId,
            long taskId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String workDescription)
    {
        this.employeeId = employeeId;
        this.taskId = taskId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workDescription = workDescription;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public long getTaskId() {
        return taskId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getWorkDescription() {
        return workDescription;
    }
}