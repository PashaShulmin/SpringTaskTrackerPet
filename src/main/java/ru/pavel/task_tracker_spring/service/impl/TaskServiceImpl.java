package ru.pavel.task_tracker_spring.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavel.task_tracker_spring.dto.TaskDTO;
import ru.pavel.task_tracker_spring.entity.Task;
import ru.pavel.task_tracker_spring.enums.TaskStatus;
import ru.pavel.task_tracker_spring.exception.DeadlineException;
import ru.pavel.task_tracker_spring.mapper.TaskMapper;
import ru.pavel.task_tracker_spring.repository.TaskRepository;
import ru.pavel.task_tracker_spring.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public void deleteTask(long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    @Transactional
    public TaskDTO edit(long taskId, TaskDTO taskDTO) throws DeadlineException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        if (taskDTO.getTitle() != null) {
            task.setTitle(taskDTO.getTitle());
        }
        if (taskDTO.getDescription() != null) {
            task.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getStatus() != null) {
            task.setStatus(taskDTO.getStatus());
        }
        if (taskDTO.getDeadline() != null) {
            if (taskDTO.getDeadline().isBefore(LocalDateTime.now())) {
                throw new DeadlineException("Дедлайн не может быть раньше текущего времени");
            }
            task.setDeadline(taskDTO.getDeadline());
        }

        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Override
    public TaskDTO add(TaskDTO taskDTO) throws DeadlineException {
        if (taskDTO.getDeadline() != null && taskDTO.getDeadline().isBefore(LocalDateTime.now())) {
            throw new DeadlineException("Дедлайн не может быть раньше текущего времени");
        }
        Task task = taskMapper.toEntity(taskDTO);
        return taskMapper.toDTO(taskRepository.save(task));
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<Task> all = taskRepository.findAll();
        return all.stream().map(taskMapper::toDTO).toList();
    }

    @Override
    public List<TaskDTO> filterTasks(String status) {
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        return taskRepository.findByStatus(taskStatus).stream().map(taskMapper::toDTO).toList();
    }

    @Override
    public List<TaskDTO> sortByStatus() {
        return taskRepository.findAllByOrderByStatusDesc().stream().map(taskMapper::toDTO).toList();
    }

    @Override
    public List<TaskDTO> sortByDeadline() {
        return taskRepository.findAllByOrderByDeadlineAsc().stream().map(taskMapper::toDTO).toList();
    }
}
