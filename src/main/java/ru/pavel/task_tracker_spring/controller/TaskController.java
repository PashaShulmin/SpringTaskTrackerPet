package ru.pavel.task_tracker_spring.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pavel.task_tracker_spring.dto.TaskDTO;
import ru.pavel.task_tracker_spring.exception.DeadlineException;
import ru.pavel.task_tracker_spring.service.TaskService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/task-tracker/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/add")
    public TaskDTO addTask(@RequestBody TaskDTO taskDTO) throws DeadlineException {
        return taskService.add(taskDTO);
    }

    @PatchMapping("/edit")
    public TaskDTO editTask(@RequestParam("id") long taskId, @RequestBody TaskDTO taskDTO) throws DeadlineException {
        return taskService.edit(taskId, taskDTO);
    }

    @DeleteMapping("/delete")
    public void deleteTask(@RequestParam("id") long taskId) {
        taskService.deleteTask(taskId);
    }

    @GetMapping("filter")
    public List<TaskDTO> filterTasks(@RequestParam("status") String status) {
        return taskService.filterTasks(status);
    }

    @GetMapping("/sort-by-status")
    public List<TaskDTO> sortByStatus(){
        return taskService.sortByStatus();
    }

    @GetMapping("/sort-by-deadline")
    public List<TaskDTO> sortByDeadline(){
        return taskService.sortByDeadline();
    }
}
