package ru.code.tasktracker.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.code.tasktracker.dto.CreateTaskRequestInfo;
import ru.code.tasktracker.dto.UpdateTaskStatusRequestInfo;
import ru.code.tasktracker.exception.GlobalExceptionHandler;
import ru.code.tasktracker.models.task.Task;
import ru.code.tasktracker.models.task.TaskStatus;
import ru.code.tasktracker.services.TaskService;

@ExtendWith(MockitoExtension.class)
public class TestTaskController
{
    @InjectMocks
    private TaskController controller;

    @Mock
    private TaskService service;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createRequest_validInput() throws Exception
    {
        CreateTaskRequestInfo requestInfo = new CreateTaskRequestInfo("Test Task", "Test Description");
        Task createdTask = new Task("Test Task", "Test Description");
        createdTask.setId(1);

        when(service.createTask(any(CreateTaskRequestInfo.class))).thenReturn(createdTask);

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestInfo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Test Task"))
                .andExpect(jsonPath("$.data.description").value("Test Description"))
                .andExpect(jsonPath("$.message").value("Create task response"));
    }

    @Test
    void createRequest_invalidInput_throwValidationFailedException() throws Exception
    {
        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bad POST request"));
    }

    @Test
    void getByIdRequest_validId_returnsOk() throws Exception
    {
        long id = 1;
        Task task = new Task("Found Task", "Found Description");
        task.setId(id);

        when(service.getTaskById(id)).thenReturn(task);

        mockMvc.perform(get("/task/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.name").value("Found Task"))
                .andExpect(jsonPath("$.message").value("Get task response by id: 1"));
    }

    @Test
    void getByIdRequest_invalidId_throwsIllegalArgumentException() throws Exception
    {
        mockMvc.perform(get("/task/{id}", -1L))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid ID"));
    }

    @Test
    void getByIdRequest_nonExistentId_throwsIllegalArgumentExceptionFromService() throws Exception
    {
        long id = 999L;
        when(service.getTaskById(id)).thenThrow(new IllegalArgumentException("Task not found: " + id));

        mockMvc.perform(get("/task/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Task not found: 999"));
    }

    @Test
    void patchStatus_validInput_returnsOk() throws Exception
    {
        long id = 1L;
        UpdateTaskStatusRequestInfo request = new UpdateTaskStatusRequestInfo(TaskStatus.DONE);
        Task updatedTask = new Task("Test Task", "Test Description");
        updatedTask.setId(id);
        updatedTask.setStatus(TaskStatus.DONE);

        when(service.updateTaskStatus(id, TaskStatus.DONE)).thenReturn(updatedTask);

        mockMvc.perform(patch("/task/{id}/status/update", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("DONE"))
                .andExpect(jsonPath("$.message").value("Patch task status. New status - DONE"));
    }

    @Test
    void patchStatus_invalidRequestBody_throwsValidationFailedException() throws Exception
    {
        UpdateTaskStatusRequestInfo invalidRequest = new UpdateTaskStatusRequestInfo(null);

        mockMvc.perform(patch("/task/{id}/status/update", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid PATCH request"));
    }

    @Test
    void patchStatus_invalidId_throwsIllegalArgumentException() throws Exception
    {
        UpdateTaskStatusRequestInfo request = new UpdateTaskStatusRequestInfo(TaskStatus.IN_PROGRESS);

        mockMvc.perform(patch("/task/{id}/status/update", -5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("invalid ID"));
    }
}