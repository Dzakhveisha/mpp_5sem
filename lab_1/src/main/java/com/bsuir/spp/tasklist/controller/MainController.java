package com.bsuir.spp.tasklist.controller;

import com.bsuir.spp.tasklist.service.InputTask;
import com.bsuir.spp.tasklist.dao.model.Task;
import com.bsuir.spp.tasklist.dao.model.TaskStatus;
import com.bsuir.spp.tasklist.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
                          @RequestParam("file") MultipartFile file,
                          BindingResult result, Model model) {
        taskService.create(task);
        List<Task> tasks = taskService.getAll();
        model.addAttribute("tasks", tasks);
        return "redirect:/taskList";
    }

    @GetMapping("/downloadFile")
    @ResponseBody
    public ResponseEntity<Resource> getFile(String filename) {
        Resource file = taskService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/delete")
    public String deleteTask(Model model, long id) {
        taskService.delete(id);
        List<Task> tasks = taskService.getAll();
        model.addAttribute("tasks", tasks);
        return "redirect:/taskList";
    }

    @GetMapping("/done")
    public String doneTask(Model model, long id) {
        taskService.done(id);
        List<Task> tasks = taskService.getAll();
        model.addAttribute("tasks", tasks);
        return "redirect:/taskList";
    }
}
