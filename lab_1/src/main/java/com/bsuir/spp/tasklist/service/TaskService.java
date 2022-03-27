package com.bsuir.spp.tasklist.service;

import com.bsuir.spp.tasklist.dao.jpa.TaskRepository;
import com.bsuir.spp.tasklist.dao.jpa.UserRepository;
import com.bsuir.spp.tasklist.dao.model.Task;
import com.bsuir.spp.tasklist.dao.model.TaskStatus;
import com.bsuir.spp.tasklist.service.exception.EntityNotFoundException;
import com.bsuir.spp.tasklist.service.model.InputTask;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
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
    private UserRepository userRepository;

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

    public List<Task> getAllForUser(Long userId) {
        if (!userRepository.findById(userId).isPresent()){
            throw new EntityNotFoundException("User", userId);
        }
        return taskRepository.findAllByUserId(userId)
                .stream()
                .map(this::checkStatus)
                .collect(Collectors.toList());
    }

    public List<Task> getAllByStatusForUser(TaskStatus status, Long userId) {
        if (!userRepository.findById(userId).isPresent()){
            throw new EntityNotFoundException("User", userId);
        }
        taskRepository.findAll()
                .stream()
                .map(this::checkStatus)
                .close();
        return taskRepository.findAllByUserIdAndStatusId(userId, status.getStatusNumber());
    }

    public void delete(long id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.deleteById(id);
        }else {
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

    public Task create(InputTask task, long userId) {
        Task newTask;
        if (!task.getFile().getOriginalFilename().equals("")) {
            try {
                Files.copy(task.getFile().getInputStream(), this.root.resolve(task.getFile().getOriginalFilename()));
            } catch (FileAlreadyExistsException e) {
            } catch (Exception e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
            newTask = new Task(task.getName(), task.getDescription(), task.getDeadline(), task.getFile().getOriginalFilename());

        } else {
            newTask = new Task(task.getName(), task.getDescription(), task.getDeadline());
        }
        newTask.setStatusId(TaskStatus.AWAIT.getStatusNumber());
        newTask.setUserId(userId);
        checkStatus(newTask);
        return taskRepository.save(newTask);
    }

    public Task getById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            return  task.get();
        } else {
            throw new EntityNotFoundException("Task", id);
        }
    }

    public Resource loadFile(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()){
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
