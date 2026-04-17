package ru.code.tasktracker.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.code.tasktracker.dto.CreateTaskRequestInfo;
import ru.code.tasktracker.exception.ObjectProcessingException;
import ru.code.tasktracker.mapper.TaskMapper;
import ru.code.tasktracker.models.task.Task;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class TestTaskService
{
    @InjectMocks
    private TaskService service;

    @Mock
    private TaskMapper mapper;

    static Stream<Arguments> provideTestTasks()
    {
        return Stream.of(
                Arguments.of( new Task("Задача 1", "Описание 1") {{setId(1);}}),
                Arguments.of(new Task("Задача 2", "Описание 2") {{setId(2);}}),
                Arguments.of(new Task("Задача 3", "Описание 3") {{setId(3);}})
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestTasks")
    void getTaskById_validId(Task expectedTask)
    {
        long expectedId = expectedTask.getId();

        Mockito.when(mapper.findById(expectedId)).thenReturn(expectedTask);

        var result = service.getTaskById(expectedId);

        Assertions.assertEquals(expectedTask, result);
        Mockito.verify(mapper).findById(expectedId);
    }

    @Test
    void createTask_correctCreateAndReturnTask()
    {
        var request = new CreateTaskRequestInfo("Новая задача", "Описание");

        Mockito.doAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            task.setId(42);
            return null;
        }).when(mapper).insert(Mockito.any(Task.class));

        var result = service.createTask(request);

        Assertions.assertEquals(42, result.getId());
        Assertions.assertEquals("Новая задача", result.getName());
        Assertions.assertEquals("Описание", result.getDescription());

        Mockito.verify(mapper).insert(Mockito.any(Task.class));
    }

    @Test
    void createTask_shouldThrowException_whenIdNotGenerated() {
        var request = new CreateTaskRequestInfo("Задача", "Описание");

        Mockito.doNothing().when(mapper).insert(Mockito.any(Task.class));

        var exception = Assertions.assertThrows(
                ObjectProcessingException.class,
                () -> service.createTask(request)
        );

        Assertions.assertEquals("Task id was not generated", exception.getMessage());
        Mockito.verify(mapper).insert(Mockito.any(Task.class));
    }

    @Test
    void isTaskExists_true()
    {
        Mockito.when(mapper.existsById(1)).thenReturn(1);
        Assertions.assertTrue(service.isTaskExists(1));
    }

    @Test
    void isTaskExists_false()
    {
        Mockito.when(mapper.existsById(999)).thenReturn(null);
        Assertions.assertFalse(service.isTaskExists(999));
    }
}
