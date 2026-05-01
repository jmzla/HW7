package edu.brooklyn.cisc3130.campus_taskboard.controller;

import edu.brooklyn.cisc3130.campus_taskboard.dto.TaskRequest;
import edu.brooklyn.cisc3130.campus_taskboard.dto.TaskResponse;
import edu.brooklyn.cisc3130.campus_taskboard.model.Task;
import edu.brooklyn.cisc3130.campus_taskboard.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //  GET ALL
    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks()
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    //  GET BY ID
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Integer id) {
        Task task = taskService.getTaskById(id);
        return TaskResponse.fromEntity(task);
    }

    //  CREATE
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest taskRequest) {

        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(taskRequest.getCompleted() != null ? taskRequest.getCompleted() : false);
        task.setPriority(Task.Priority.valueOf(
                taskRequest.getPriority() != null
                        ? taskRequest.getPriority().toUpperCase()
                        : "MEDIUM"));

        Task createdTask = taskService.createTask(task);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TaskResponse.fromEntity(createdTask));
    }

    // UPDATE
    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable Integer id,
            @Valid @RequestBody TaskRequest taskRequest) {

        Task updatedTask = new Task();
        updatedTask.setTitle(taskRequest.getTitle());
        updatedTask.setDescription(taskRequest.getDescription());
        updatedTask.setCompleted(taskRequest.getCompleted());
        updatedTask.setPriority(Task.Priority.valueOf(
                taskRequest.getPriority() != null
                        ? taskRequest.getPriority().toUpperCase()
                        : "MEDIUM"));

        Task task = taskService.updateTask(id, updatedTask);
        return TaskResponse.fromEntity(task);
    }

    // DELETE (SOFT DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    //  RESTORE (BONUS)
    @PutMapping("/{id}/restore")
    public ResponseEntity<Void> restoreTask(@PathVariable Integer id) {
        taskService.restoreTask(id);
        return ResponseEntity.ok().build();
    }
}