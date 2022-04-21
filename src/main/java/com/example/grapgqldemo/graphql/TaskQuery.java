package com.example.grapgqldemo.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.grapgqldemo.model.Post;
import com.example.grapgqldemo.model.Task;
import com.example.grapgqldemo.model.TaskStatus;
import com.example.grapgqldemo.service.PostService;
import com.example.grapgqldemo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskQuery implements GraphQLQueryResolver {

    @Autowired
    private TaskService taskService;


    public List<Task> getTasks() {
        return taskService.getAll();
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskService.getAllByStatus(status);
    }
}
