package ru.pavel.task_tracker_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pavel.task_tracker_spring.entity.Task;
import ru.pavel.task_tracker_spring.enums.TaskStatus;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus taskStatus);

    List<Task> findAllByOrderByStatusDesc();

    List<Task> findAllByOrderByDeadlineAsc();
}
