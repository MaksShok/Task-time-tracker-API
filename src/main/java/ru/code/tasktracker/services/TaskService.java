package ru.code.tasktracker.services;

import org.springframework.stereotype.Service;
import ru.code.tasktracker.dto.CreateTaskRequestInfo;
import ru.code.tasktracker.mapper.TaskMapper;
import ru.code.tasktracker.models.task.Task;

@Service
public class TaskService
{
    private final TaskMapper mapper;

    public TaskService(TaskMapper mapper)
    {
        this.mapper = mapper;
    }

    public Task createTask(CreateTaskRequestInfo taskInfo)
    {
        var task = new Task(taskInfo.name, taskInfo.description);

        mapper.insert(task);
        return task;
    }

    public Task getTask(long id)
    {
        var task = mapper.findById(id);
        return task;
    }
}
