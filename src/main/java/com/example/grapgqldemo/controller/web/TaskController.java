package com.example.grapgqldemo.controller.web;


import com.example.grapgqldemo.model.Task;
import com.example.grapgqldemo.model.TaskStatus;
import com.example.grapgqldemo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    @GetMapping("")
    public List<Task> getByStatus(@RequestParam(required = false) TaskStatus status) {
        if (status == null) {
            return taskService.getAll();
        }
        return taskService.getAllByStatus(status);
    }

    @PostMapping("")
    public Task newTask(@RequestParam long id, @ModelAttribute Task task) {
        return taskService.create(task);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Task getTask(@PathVariable long id) {
        return taskService.getById(id);
    }

    @GetMapping("/{id}/file")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable long id) {
        Resource file = taskService.loadFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable long id) {
        taskService.delete(id);
    }

    @PutMapping("/{id}")
    public void doneTask(@PathVariable long id) {
        taskService.done(id);
    }
}
