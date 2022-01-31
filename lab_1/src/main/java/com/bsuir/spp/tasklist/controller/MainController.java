package com.bsuir.spp.tasklist.controller;

import com.bsuir.spp.tasklist.dao.model.Task;
import com.bsuir.spp.tasklist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/taskList")
public class MainController {

    private TaskService taskService;

    @GetMapping
    public String getMainPage() {
        List<Task> tasks = taskService.getAll();
        return "MainPage";
    }

    @GetMapping("/new")
    public void newTask() {

    }
}
