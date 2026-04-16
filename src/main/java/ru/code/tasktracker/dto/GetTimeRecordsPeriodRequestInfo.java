package ru.code.tasktracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class GetTimeRecordsPeriodRequestInfo
{
    @NotNull(message = "Employee ID must not be null")
    @Positive(message = "Employee ID must be positive")
    private Long employeeId;

    @NotNull(message = "Start date must not be null")
    private LocalDateTime startDate;

    @NotNull(message = "End date must not be null")
    private LocalDateTime endDate;

    public GetTimeRecordsPeriodRequestInfo(
            Long employeeId,
            LocalDateTime startDate,
            LocalDateTime endDate)
    {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}