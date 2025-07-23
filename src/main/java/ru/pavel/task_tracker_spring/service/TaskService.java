package ru.pavel.task_tracker_spring.service;

import ru.pavel.task_tracker_spring.dto.TaskDTO;
import ru.pavel.task_tracker_spring.exception.DeadlineException;

import java.util.List;

public interface TaskService {
    void deleteTask(long taskId);

    TaskDTO edit(long taskId, TaskDTO taskDTO) throws DeadlineException;

    TaskDTO add(TaskDTO taskDTO) throws DeadlineException;

    List<TaskDTO> getAllTasks();

    List<TaskDTO> filterTasks(String status);

    List<TaskDTO> sortByStatus();

    List<TaskDTO> sortByDeadline();
}
