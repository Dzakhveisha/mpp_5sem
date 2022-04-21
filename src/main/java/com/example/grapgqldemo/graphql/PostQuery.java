package com.example.grapgqldemo.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.grapgqldemo.model.Post;
import com.example.grapgqldemo.model.Task;
import com.example.grapgqldemo.service.PostService;
import com.example.grapgqldemo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostQuery implements GraphQLQueryResolver {

    @Autowired
    private PostService postService;

    @Autowired
    private TaskService taskService;


    public List<Task> getTasks() {
        return taskService.getAll();
    }
}
