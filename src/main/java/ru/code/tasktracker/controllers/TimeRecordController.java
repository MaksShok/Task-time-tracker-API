package ru.code.tasktracker.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.code.tasktracker.dto.ApiResponse;
import ru.code.tasktracker.dto.CreateTimeRecordRequestInfo;
import ru.code.tasktracker.dto.GetTimeRecordsPeriodRequestInfo;
import ru.code.tasktracker.exception.ValidationFailedException;
import ru.code.tasktracker.models.timerecord.TimeRecord;
import ru.code.tasktracker.services.TimeRecordService;

import java.util.List;

@RestController
@RequestMapping("/time-record")
public class TimeRecordController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(TimeRecordController.class);
    private final TimeRecordService service;

    public TimeRecordController(TimeRecordService service)
    {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TimeRecord>> createTimeRecord(
            @Valid @RequestBody CreateTimeRecordRequestInfo requestInfo,
            BindingResult valid)
    {
        if (valid.hasErrors())
            throw new ValidationFailedException("Bad POST request");

        var record = service.createTimeRecord(requestInfo);

        String message = "Time record created with id " + record.getId();
        return toResponse(HttpStatus.CREATED, record, message);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TimeRecord>>> getTimeRecordsByPeriod(
            @Valid @ModelAttribute GetTimeRecordsPeriodRequestInfo requestInfo,
            BindingResult valid)
    {
        if (valid.hasErrors())
            throw new ValidationFailedException("Bad GET request");

        var records = service.getTimeRecordsByPeriod(
                requestInfo.getEmployeeId(),
                requestInfo.getStartDate(),
                requestInfo.getEndDate()
        );

        String message = "Found " + records.size() + " time record(s)";
        return toResponse(HttpStatus.OK, records, message);
    }

    @Override
    protected Logger getLogger() {return log;}
}