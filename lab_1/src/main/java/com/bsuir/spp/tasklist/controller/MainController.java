package com.bsuir.spp.tasklist.controller;

import com.bsuir.spp.tasklist.dao.model.Task;
import com.bsuir.spp.tasklist.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/taskList")
public class MainController {

    private TaskService taskService;

    @GetMapping
    public String getMainPage(Model model) {
        List<Task> tasks = taskService.getAll();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/new")
    public void newTask(Model model) {

    }
}
