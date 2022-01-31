package com.bsuir.spp.tasklist.service;

import com.bsuir.spp.tasklist.dao.jpa.TaskRepository;
import com.bsuir.spp.tasklist.dao.model.Task;
import com.bsuir.spp.tasklist.dao.model.TaskStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public void delete(long id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.deleteById(id);
        }
    }

    public void create(Task task){
        task.setDeadline(LocalDateTime.now());                 ///why null??
        task.setStatusId(TaskStatus.AWAIT.getStatusNumber());
        taskRepository.save(task);
    }
}
