package com.example.grapgqldemo.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.grapgqldemo.model.Task;
import com.example.grapgqldemo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskMutation implements GraphQLMutationResolver {
    private TaskService service;

    public Task createTask(String name, String description, String deadline) {
        return service.create(new Task(name,description,deadline));
    }

    public Task deleteTask(long id) {
        Task task = service.getById(id);
        service.delete(id);
        return task;
    }

    public Task doneTask(long id) {
        return service.done(id);
    }
}
