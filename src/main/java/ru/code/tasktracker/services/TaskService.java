package ru.code.tasktracker.services;

import org.springframework.stereotype.Service;
import ru.code.tasktracker.dto.CreateTaskRequestInfo;
import ru.code.tasktracker.exception.ObjectProcessingException;
import ru.code.tasktracker.mapper.TaskMapper;
import ru.code.tasktracker.models.task.Task;
import ru.code.tasktracker.models.task.TaskStatus;

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
        var task = new Task(taskInfo.getName(), taskInfo.getDescription());
        mapper.insert(task);

        if (task.getId() <= 0)
            throw new ObjectProcessingException("Task id was not generated");

        return task;
    }

    public Task getTaskById(long id)
    {
        var task = mapper.findById(id);

        if (task == null)
            throw new IllegalArgumentException("Task not found: " + id);

        return task;
    }

    public Task updateTaskStatus(long id, TaskStatus status)
    {
        if (status == null)
            throw new IllegalArgumentException("Invalid status");

        var task = getTaskById(id);

        mapper.updateStatus(id, status);
        task.setStatus(status);

        return task;
    }

    public Boolean isTaskExists(long id)
    {
        return mapper.existsById(id) != null;
    }
}
