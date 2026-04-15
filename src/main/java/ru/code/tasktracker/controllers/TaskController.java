package ru.code.tasktracker.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.code.tasktracker.dto.CreateTaskRequestInfo;
import ru.code.tasktracker.exception.BadRequestException;
import ru.code.tasktracker.models.task.Task;
import ru.code.tasktracker.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController
{
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);
    private final TaskService service;

    public TaskController(TaskService service)
    {
        this.service = service;
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Task processCreateRequest(@Valid @RequestBody CreateTaskRequestInfo taskInfo, BindingResult valid)
    {
        if (valid.hasErrors())
            throw new BadRequestException("Bad POST request");

        var task = service.createTask(taskInfo);
        log.info("Create task response: {}", task);
        return task;
    }

    @GetMapping("/{id}")
    public Task processGetByIdRequest(@PathVariable long id)
    {
        var task = service.getTask(id);
        log.info("Get task response: {}", task);
        return task;
    }
}
