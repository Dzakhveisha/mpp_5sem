package com.bsuir.spp.tasklist.service;

import com.bsuir.spp.tasklist.dao.jpa.TaskRepository;
import com.bsuir.spp.tasklist.dao.model.InputTask;
import com.bsuir.spp.tasklist.dao.model.Task;
import com.bsuir.spp.tasklist.dao.model.TaskStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;

    public List<Task> getAll() {
        return taskRepository.findAll()
                .stream()
                .map(this::checkStatus)
                .collect(Collectors.toList());
    }

    public List<Task> getAllByStatus(TaskStatus status) {
        taskRepository.findAll()
                .stream()
                .map(this::checkStatus)
                .close();
        return taskRepository.findAllByStatusId(status.getStatusNumber());
    }

    public void delete(long id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.deleteById(id);
        }
    }

    public void create(InputTask task) {
        Task newTask = new Task(task.getName(), task.getDescription(), task.getDeadline());
        newTask.setStatusId(TaskStatus.AWAIT.getStatusNumber());
        checkStatus(newTask);
        taskRepository.save(newTask);
    }

    private Task checkStatus(Task task) {
        if (task.getStatus().equals(TaskStatus.AWAIT) && task.getDeadline().isBefore(LocalDateTime.now())) {
            task.setStatusId(TaskStatus.EXPIRED.getStatusNumber());
            taskRepository.save(task);
        }
        return task;
    }
}
