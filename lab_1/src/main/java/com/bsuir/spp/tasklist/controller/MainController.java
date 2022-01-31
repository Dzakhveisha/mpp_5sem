package com.bsuir.spp.tasklist.controller;

import com.bsuir.spp.tasklist.dao.model.Task;
import com.bsuir.spp.tasklist.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/showNewTask")
    public String showForm(Model model) {
        model.addAttribute("task", new Task());
        return "newTaskForm";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newTask(@ModelAttribute("task") Task task,
                   BindingResult result, Model model) {

        taskService.create(task);
        List<Task> tasks = taskService.getAll();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/delete")
    public String deleteTask(Model model, long id) {
        taskService.delete(id);
        List<Task> tasks = taskService.getAll();
        model.addAttribute("tasks", tasks);
        return "index";
    }

}
