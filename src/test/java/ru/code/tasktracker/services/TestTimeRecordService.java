package ru.code.tasktracker.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.code.tasktracker.dto.CreateTimeRecordRequestInfo;
import ru.code.tasktracker.mapper.TaskMapper;
import ru.code.tasktracker.models.timerecord.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TestTimeRecordService
{
    @InjectMocks
    private TimeRecordService service;

    @Mock
    private TaskService taskService;

    @Mock
    private TaskMapper mapper;

    static Stream<Arguments> provideValidTimeRecords()
    {
        return Stream.of(
                Arguments.of(1, 101, "2026-04-17T09:00:00", "2026-04-17T17:00:00", "Работа над API"),
                Arguments.of(2, 102, "2026-04-18T10:00:00", "2026-04-18T18:00:00", "Тестирование"),
                Arguments.of(3, 103, "2026-04-19T08:00:00", "2026-04-19T16:00:00", "Документация")
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidTimeRecords")
    void createTimeRecord_success(long taskId, long employeeId, String start, String end, String description)
    {
        var startTime = LocalDateTime.parse(start);
        var endTime = LocalDateTime.parse(end);
        var request = new CreateTimeRecordRequestInfo(employeeId, taskId, startTime, endTime, description);

        Mockito.when(taskService.isTaskExists(taskId)).thenReturn(true);

        Mockito.doAnswer(invocation -> {
            TimeRecord record = invocation.getArgument(0);
            record.setId(42);
            return null;
        }).when(mapper).insertTimeRecord(Mockito.any(TimeRecord.class));

        var result = service.createTimeRecord(request);

        assertEquals(42, result.getId());
        assertEquals(taskId, result.getTaskId());
        assertEquals(employeeId, result.getEmployeeId());
        assertEquals(startTime, result.getStartTime());
        assertEquals(endTime, result.getEndTime());
        assertEquals(description, result.getWorkDescription());

        Mockito.verify(taskService).isTaskExists(taskId);
        Mockito.verify(mapper).insertTimeRecord(Mockito.any(TimeRecord.class));
    }

    @Test
    void createTimeRecord_taskNotFound()
    {
        var request = new CreateTimeRecordRequestInfo(
                101, 999,
                LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                "Описание"
        );

        Mockito.when(taskService.isTaskExists(999)).thenReturn(false);

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createTimeRecord(request)
        );

        assertEquals("Task with id: {999}not exists for create time-record", exception.getMessage());
        Mockito.verify(taskService).isTaskExists(999L);
        Mockito.verifyNoInteractions(mapper);
    }

    @Test
    void createTimeRecord_invalidTimeRange()
    {
        var request = new CreateTimeRecordRequestInfo(
                101, 1,
                LocalDateTime.of(2026, 4, 17, 17, 0), // start
                LocalDateTime.of(2026, 4, 17, 9, 0),  // end (раньше start)
                "Описание"
        );

        Mockito.when(taskService.isTaskExists(1)).thenReturn(true);

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createTimeRecord(request)
        );

        assertEquals("Invalid time-record range", exception.getMessage());
        Mockito.verify(taskService).isTaskExists(1L);
        Mockito.verifyNoInteractions(mapper);
    }

    @Test
    void getTimeRecordsByPeriod_success()
    {
        long employeeId = 101;
        var startDate = LocalDateTime.of(2026, 4, 1, 0, 0);
        var endDate = LocalDateTime.of(2026, 4, 30, 23, 59);

        var record1 = new TimeRecord();
        record1.setId(1);
        record1.setEmployeeId(employeeId);

        var record2 = new TimeRecord();
        record2.setId(2);
        record2.setEmployeeId(employeeId);

        var expectedRecords = List.of(record1, record2);

        Mockito.when(mapper.findTimeRecordsByEmployeeAndPeriod(employeeId, startDate, endDate))
                .thenReturn(expectedRecords);

        var result = service.getTimeRecordsByPeriod(employeeId, startDate, endDate);

        assertEquals(2, result.size());
        assertEquals(expectedRecords, result);
        Mockito.verify(mapper).findTimeRecordsByEmployeeAndPeriod(employeeId, startDate, endDate);
    }

    @Test
    void getTimeRecordsByPeriod_invalidRange()
    {
        long employeeId = 101;
        var startDate = LocalDateTime.of(2026, 4, 30, 23, 59);
        var endDate = LocalDateTime.of(2026, 4, 1, 0, 0); // end раньше start

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.getTimeRecordsByPeriod(employeeId, startDate, endDate)
        );

        assertEquals("Invalid time-record range", exception.getMessage());
        Mockito.verifyNoInteractions(mapper);
    }
}