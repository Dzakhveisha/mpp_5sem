package com.example.grapgqldemo.service;


import com.example.grapgqldemo.model.InputTask;
import com.example.grapgqldemo.model.Task;
import com.example.grapgqldemo.model.TaskStatus;
import com.example.grapgqldemo.repository.TaskRepository;
import com.example.grapgqldemo.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    private final Path root = Paths.get("uploads");

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
        } else {
            throw new EntityNotFoundException("Task", id);
        }

    }

    public void done(long id) {
        if (taskRepository.findById(id).isPresent()) {
            Task task = taskRepository.getById(id);
            task.setStatusId(TaskStatus.DONE.getStatusNumber());
            taskRepository.save(task);
        } else {
            throw new EntityNotFoundException("Task", id);
        }
    }

    public Task create(Task task) {
        Task newTask;
        newTask = new Task(task.getName(), task.getDescription(), task.getDeadline().toString());
        newTask.setStatusId(TaskStatus.AWAIT.getStatusNumber());
        newTask.setUserId(null);
        checkStatus(newTask);
        return taskRepository.save(newTask);
    }

    public Task addFile(InputTask task, long id) {
        Task editedTask = getById(id);
        if(task.getFile() != null) {
            if (!task.getFile().getOriginalFilename().equals("")) {
                try {
                    Files.copy(task.getFile().getInputStream(), this.root.resolve(task.getFile().getOriginalFilename()));
                    editedTask.setFileName(task.getFile().getOriginalFilename());
                    taskRepository.save(editedTask);
                } catch (FileAlreadyExistsException e) {
                } catch (Exception e) {
                    throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
                }
            }
        }
        return editedTask;
    }

    public Task getById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            return task.get();
        } else {
            throw new EntityNotFoundException("Task", id);
        }
    }

    public Resource loadFile(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            throw new EntityNotFoundException("Task", id);
        }
        Task task = optionalTask.get();
        String filename = task.getFileName() != null ? task.getFileName() : "";
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private Task checkStatus(Task task) {
        if (task.getStatus().equals(TaskStatus.AWAIT) && task.getDeadline().isBefore(LocalDateTime.now())) {
            task.setStatusId(TaskStatus.EXPIRED.getStatusNumber());
            taskRepository.save(task);
        }
        return task;
    }
}

