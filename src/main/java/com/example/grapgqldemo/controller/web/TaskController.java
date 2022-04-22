package com.example.grapgqldemo.controller.web;


import com.example.grapgqldemo.model.InputTask;
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

    @PostMapping("/{id}/file")
    public Task addFile(@PathVariable long id, @ModelAttribute InputTask task) {
        return taskService.addFile(task, id);
    }

    @GetMapping("/{id}/file")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable long id) {
        Resource file = taskService.loadFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PutMapping("/{id}")
    public void doneTask(@PathVariable long id) {
        taskService.done(id);
    }
}
