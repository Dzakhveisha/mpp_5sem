package com.example.grapgqldemo.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.grapgqldemo.model.Task;
import com.example.grapgqldemo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostMutation implements GraphQLMutationResolver {
    private TaskService service;

    public Task createTask(String name, String description, String deadline) {
        return service.create(new Task(name,description,deadline));
    }
}
