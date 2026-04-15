package ru.code.tasktracker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("ru.code.tasktracker.mapper")
public class TaskTrackerApiApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(TaskTrackerApiApplication.class, args);
    }
}
