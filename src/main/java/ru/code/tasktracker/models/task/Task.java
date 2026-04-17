package ru.code.tasktracker.models.task;

public class Task
{
    private long id = -1;
    private String name;
    private String description;
    private TaskStatus status;

    public Task(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id == task.id
                && name == task.name
                && description == task.description
                && status == task.status;
    }
}

