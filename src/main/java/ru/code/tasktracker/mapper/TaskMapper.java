package ru.code.tasktracker.mapper;

import org.apache.ibatis.annotations.*;
import ru.code.tasktracker.models.task.Task;
import ru.code.tasktracker.models.task.TaskStatus;
import ru.code.tasktracker.models.timerecord.TimeRecord;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TaskMapper
{
    @Insert("INSERT INTO tasks(name, description, status) VALUES(#{name}, #{description}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insert(Task task);

    @Select("SELECT * FROM tasks WHERE id = #{id}")
    public Task findById(long id);

    @Select("SELECT 1 FROM tasks WHERE id = #{id} LIMIT 1")
    public Integer existsById(@Param("id") long id);

    @Update("UPDATE tasks SET status = #{status} WHERE id = #{id}")
    public void updateStatus(@Param("id") long id, @Param("status") TaskStatus status);

    @Insert("INSERT INTO time_records(employee_id, task_id, start_date_time, end_date_time, work_description) " +
            "VALUES(#{employeeId}, #{taskId}, #{startDateTime}, #{endDateTime}, #{workDescription})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertTimeRecord(TimeRecord record);

    @Select("SELECT * FROM time_records " +
            "WHERE employee_id = #{employeeId} " +
            "AND start_date_time >= #{startDateTime} " +
            "AND end_date_time <= #{endDateTime}")
    List<TimeRecord> findTimeRecordsByEmployeeAndPeriod(
            @Param("employeeId") long employeeId,
            @Param("startDateTime") LocalDateTime startDate,
            @Param("endDateTime") LocalDateTime endDate
    );
}
