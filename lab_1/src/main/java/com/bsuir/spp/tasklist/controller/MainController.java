package com.bsuir.spp.tasklist.controller;

import com.bsuir.spp.tasklist.dao.model.InputTask;
import com.bsuir.spp.tasklist.dao.model.Task;
import com.bsuir.spp.tasklist.dao.model.TaskStatus;
import com.bsuir.spp.tasklist.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Controller
@RequestMapping("/taskList")
public class MainController {

    private TaskService taskService;

    @GetMapping({""})
    public String getMainPage(Model model) {
        List<Task> tasks = taskService.getAll();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/filter")
    public String getFilteredMainPage(Model model, TaskStatus status){
        List<Task> tasks = taskService.getAllByStatus(status);
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/showNewTask")
    public String showForm(Model model) {
        model.addAttribute("task", new InputTask());
        return "newTaskForm";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newTask(@ModelAttribute("task") InputTask task,
                          BindingResult result, Model model) {
        taskService.create(task);
        List<Task> tasks = taskService.getAll();
        model.addAttribute("tasks", tasks);
        return "redirect:/taskList";
    }

    @GetMapping("/delete")
    public String deleteTask(Model model, long id) {
        taskService.delete(id);
        List<Task> tasks = taskService.getAll();
        model.addAttribute("tasks", tasks);
        return "redirect:/taskList";
    }

}
