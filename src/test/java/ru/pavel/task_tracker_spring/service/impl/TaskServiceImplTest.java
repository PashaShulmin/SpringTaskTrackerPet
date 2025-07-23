package ru.pavel.task_tracker_spring.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.pavel.task_tracker_spring.dto.TaskDTO;
import ru.pavel.task_tracker_spring.entity.Task;
import ru.pavel.task_tracker_spring.enums.TaskStatus;
import ru.pavel.task_tracker_spring.exception.DeadlineException;
import ru.pavel.task_tracker_spring.mapper.TaskMapper;
import ru.pavel.task_tracker_spring.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void add_ShouldSaveTask_WhenDeadlineIsValid() throws DeadlineException {
        TaskDTO dto = new TaskDTO();
        dto.setTitle("Test");
        dto.setDeadline(LocalDateTime.now().plusDays(1));
        dto.setDescription("Description");
        Task task = new Task();
        when(taskMapper.toEntity(dto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDTO(task)).thenReturn(dto);

        TaskDTO result = taskService.add(dto);

        Assertions.assertEquals(dto, result);
        verify(taskRepository).save(task);
    }

    @Test
    void add_ShouldThrowException_WhenDeadlineInPast() {
        TaskDTO dto = new TaskDTO();
        dto.setDeadline(LocalDateTime.now().minusDays(1));

        Assertions.assertThrows(DeadlineException.class, () -> taskService.add(dto));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void edit_ShouldUpdateFields_WhenFieldsPresent() throws DeadlineException {
        long id = 1L;
        TaskDTO dto = new TaskDTO();
        dto.setTitle("New title");
        dto.setDeadline(LocalDateTime.now().plusDays(2));
        Task task = new Task();
        task.setId(id);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDTO(task)).thenReturn(dto);

        TaskDTO result = taskService.edit(id, dto);

        Assertions.assertEquals(dto.getTitle(), result.getTitle());
        verify(taskRepository).save(task);
    }

    @Test
    void getAllTasks_ShouldReturnList() {
        List<Task> tasks = List.of(new Task());
        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.toDTO(any())).thenReturn(new TaskDTO());

        List<TaskDTO> result = taskService.getAllTasks();

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void filterTasks_ShouldReturnFilteredList() {
        List<Task> tasks = List.of(new Task());
        when(taskRepository.findByStatus(TaskStatus.TODO)).thenReturn(tasks);
        when(taskMapper.toDTO(any())).thenReturn(new TaskDTO());

        List<TaskDTO> result = taskService.filterTasks("TODO");

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void sortByStatus_ShouldReturnSortedList() {
        List<Task> tasks = List.of(new Task());
        when(taskRepository.findAllByOrderByStatusDesc()).thenReturn(tasks);
        when(taskMapper.toDTO(any())).thenReturn(new TaskDTO());

        List<TaskDTO> result = taskService.sortByStatus();

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void sortByDeadline_ShouldReturnSortedList() {
        List<Task> tasks = List.of(new Task());
        when(taskRepository.findAllByOrderByDeadlineAsc()).thenReturn(tasks);
        when(taskMapper.toDTO(any())).thenReturn(new TaskDTO());

        List<TaskDTO> result = taskService.sortByDeadline();

        Assertions.assertEquals(1, result.size());
    }
}