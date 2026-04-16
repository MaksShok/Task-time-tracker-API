package ru.code.tasktracker.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.code.tasktracker.dto.CreateTimeRecordRequestInfo;
import ru.code.tasktracker.exception.ObjectProcessingException;
import ru.code.tasktracker.mapper.TaskMapper;
import ru.code.tasktracker.models.timerecord.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeRecordService
{
    private final TaskService taskService;
    private final TaskMapper mapper;

    public TimeRecordService(TaskService taskService, TaskMapper mapper)
    {
        this.taskService = taskService;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public TimeRecord createTimeRecord(CreateTimeRecordRequestInfo requestInfo)
    {
        long taskId = requestInfo.getTaskId();
        if (!taskService.isTaskExists(taskId))
        {
            throw new IllegalArgumentException("Task with id: {"+ taskId + "}not exists for create time-record");
        }

        var startDateTime = requestInfo.getStartTime();
        var endDateTime = requestInfo.getEndTime();
        if (startDateTime.isAfter(endDateTime))
        {
            throw new IllegalArgumentException("Invalid time-record range");
        }

        var record = new TimeRecord();
        record.setTaskId(taskId);
        record.setEmployeeId(requestInfo.getEmployeeId());
        record.setStartTime(startDateTime);
        record.setEndTime(endDateTime);
        record.setWorkDescription(requestInfo.getWorkDescription());

        mapper.insertTimeRecord(record);

        if (record.getId() <= 0)
            throw new ObjectProcessingException("Time record id was not generated");

        return record;
    }

    public List<TimeRecord> getTimeRecordsByPeriod(
            long employeeId,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime)
    {
        if (startDateTime.isAfter(endDateTime))
        {
            throw new IllegalArgumentException("Invalid time-record range");
        }

        return mapper.findTimeRecordsByEmployeeAndPeriod(employeeId, startDateTime, endDateTime);
    }
}