package ru.pavel.task_tracker_spring.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pavel.task_tracker_spring.dto.TaskDTO;
import ru.pavel.task_tracker_spring.entity.Task;
import ru.pavel.task_tracker_spring.enums.TaskStatus;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, TaskStatus.class})
public interface TaskMapper {
//    @Mapping(target = "deadline", expression = "java(LocalDateTime.parse(dto.getDeadline()))")
//    @Mapping(target = "status", expression = "java(TaskStatus.valueOf(dto.getStatus()))")
    Task toEntity(TaskDTO dto);

//    @Mapping(target = "deadline", expression = "java(task.getDeadline().toString())")
//    @Mapping(target = "status", expression = "java(task.getStatus().name())")
    TaskDTO toDTO(Task task);
}