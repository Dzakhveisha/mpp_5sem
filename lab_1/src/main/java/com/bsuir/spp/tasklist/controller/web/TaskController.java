package com.bsuir.spp.tasklist.controller.web;

import com.bsuir.spp.tasklist.dao.model.Task;
import com.bsuir.spp.tasklist.dao.model.TaskStatus;
import com.bsuir.spp.tasklist.service.InputTask;
import com.bsuir.spp.tasklist.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    @GetMapping("")
    public List<Task> getAll(@RequestParam(required = false) TaskStatus status) {
        if (status == null){
            return  taskService.getAll();
        }
        return taskService.getAllByStatus(status);
    }

    @PostMapping("")
    public Task newTask(InputTask task) {
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
