package ru.code.tasktracker.services;

import org.springframework.stereotype.Service;
import ru.code.tasktracker.dto.CreateTaskRequestInfo;
import ru.code.tasktracker.mapper.TaskMapper;
import ru.code.tasktracker.models.Task.Task;

import java.util.ArrayList;

@Service
public class TaskService
{
    private final TaskMapper mapper;

    public TaskService(TaskMapper mapper)
    {
        this.mapper = mapper;
    }

    private int counter = 0;
    public Task createTask(CreateTaskRequestInfo taskInfo)
    {
        var task = new Task(taskInfo.name, taskInfo.description);

        //Test
        task.setId(++counter);;
        //Обращение к маппер insert
        return task;
    }
}
