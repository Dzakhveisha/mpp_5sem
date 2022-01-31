package com.bsuir.spp.tasklist.service;

import com.bsuir.spp.tasklist.dao.jpa.TaskRepository;
import com.bsuir.spp.tasklist.dao.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;

    public List<Task> getAll(){
        return taskRepository.findAll();
    }
}
