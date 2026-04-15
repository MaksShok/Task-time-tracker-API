package ru.code.tasktracker.controllers;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.code.tasktracker.dto.CreateTaskRequestInfo;
import ru.code.tasktracker.exception.BadRequestException;
import ru.code.tasktracker.services.TaskService;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/task")
public class TaskController
{
    private final TaskService service;

    public TaskController(TaskService service)
    {
        this.service = service;
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public void processCreateRequest(@Valid @RequestBody CreateTaskRequestInfo taskInfo, BindingResult valid)
    {
        if (valid.hasErrors())
            throw new BadRequestException("Bad POST request");

        var task = service.createTask(taskInfo);
    }

    /*private TaskResponse toResponse(CreateTaskRequestInfo taskInfo)
    {

    }*/
}
