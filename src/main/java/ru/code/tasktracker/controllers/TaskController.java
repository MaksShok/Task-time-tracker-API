package ru.code.tasktracker.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.code.tasktracker.dto.ApiResponse;
import ru.code.tasktracker.dto.CreateTaskRequestInfo;
import ru.code.tasktracker.dto.UpdateTaskStatusRequestInfo;
import ru.code.tasktracker.exception.ValidationFailedException;
import ru.code.tasktracker.models.task.Task;
import ru.code.tasktracker.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);
    private final TaskService service;

    public TaskController(TaskService service)
    {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Task>> createRequest(@Valid @RequestBody CreateTaskRequestInfo taskInfo, BindingResult valid)
    {
        if (valid.hasErrors())
            throw new ValidationFailedException("Bad POST request");

        var task = service.createTask(taskInfo);

        String message = "Create task response";
        return toResponse(HttpStatus.CREATED, task, message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> getByIdRequest(@PathVariable long id)
    {
        if (id <= 0)
            throw new IllegalArgumentException("Invalid ID");

        var task = service.getTaskById(id);

        String message = "Get task response by id: " + id;
        return toResponse(HttpStatus.OK, task, message);
    }

    @PatchMapping("/{id}/status/update")
    public ResponseEntity<ApiResponse<Task>> patchStatus(@PathVariable long id, @Valid @RequestBody UpdateTaskStatusRequestInfo request, BindingResult valid)
    {
        if (valid.hasErrors())
            throw new ValidationFailedException("Invalid PATCH request");

        if (id <= 0)
            throw new IllegalArgumentException("invalid ID");

        var task = service.updateTaskStatus(id, request.getStatus());

        String message = "Patch task status. New status - " + task.getStatus();
        return toResponse(HttpStatus.OK, task, message);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
