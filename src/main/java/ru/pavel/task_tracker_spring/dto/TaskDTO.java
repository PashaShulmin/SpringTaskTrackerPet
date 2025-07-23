package ru.pavel.task_tracker_spring.dto;

import lombok.Data;
import ru.pavel.task_tracker_spring.enums.TaskStatus;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private String title;
    private String description;
    private TaskStatus status = TaskStatus.TODO;
    private LocalDateTime deadline;
}
