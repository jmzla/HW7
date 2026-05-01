package edu.brooklyn.cisc3130.campus_taskboard.service;

import edu.brooklyn.cisc3130.campus_taskboard.exception.TaskNotFoundException;
import edu.brooklyn.cisc3130.campus_taskboard.model.Task;
import edu.brooklyn.cisc3130.campus_taskboard.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findByDeletedFalse();
    }

    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id)
                .filter(task -> !task.getDeleted())
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task task) {
        if (task.getCompleted() == null) {
            task.setCompleted(false);
        }

        if (task.getPriority() == null) {
            task.setPriority(Task.Priority.MEDIUM);
        }

        if (task.getDeleted() == null) {
            task.setDeleted(false);
        }

        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, Task updatedTask) {
        Task task = getTaskById(id);

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.getCompleted());
        task.setPriority(updatedTask.getPriority());

        return taskRepository.save(task);
    }

    public void deleteTask(Integer id) {
        Task task = getTaskById(id);
        task.setDeleted(true);
        taskRepository.save(task);
    }

    public void restoreTask(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setDeleted(false);
        taskRepository.save(task);
    }

    public List<Task> getDeletedTasks() {
        return taskRepository.findByDeletedTrue();
    }

    public List<Task> getCompletedTasks() {
        return taskRepository.findByCompletedTrue()
                .stream()
                .filter(task -> !task.getDeleted())
                .toList();
    }

    public List<Task> getIncompleteTasks() {
        return taskRepository.findByCompletedFalse()
                .stream()
                .filter(task -> !task.getDeleted())
                .toList();
    }

    public List<Task> getTasksByPriority(Task.Priority priority) {
        return taskRepository.findByPriority(priority)
                .stream()
                .filter(task -> !task.getDeleted())
                .toList();
    }

    public List<Task> searchTasks(String keyword) {
        return taskRepository.searchTasks(keyword)
                .stream()
                .filter(task -> !task.getDeleted())
                .toList();
    }

    public Page<Task> getCompletedTasks(Pageable pageable) {
        return taskRepository.findByCompletedTrue(pageable);
    }
}