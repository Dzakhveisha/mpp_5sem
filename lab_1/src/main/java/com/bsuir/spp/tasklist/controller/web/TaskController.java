package com.bsuir.spp.tasklist.controller.web;

import com.bsuir.spp.tasklist.dao.model.Task;
import com.bsuir.spp.tasklist.dao.model.TaskStatus;
import com.bsuir.spp.tasklist.service.model.InputTask;
import com.bsuir.spp.tasklist.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('USER')")    //(hasAuthority('USER') AND #id == principal.id)
    public List<Task> getAllForUser(@RequestParam(required = false) TaskStatus status,
                             @RequestParam(required = false) Long userId) {
        if (userId == null) {
            if (status == null) {
                return taskService.getAll();
            }
            return taskService.getAllByStatus(status);

        } else {
            if (status == null) {
                return taskService.getAllForUser(userId);
            }
            return taskService.getAllByStatusForUser(status, userId);
        }
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public Task newTask(@PathVariable long id, @RequestBody InputTask task) {
        return taskService.create(task, id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseBody
    public Task getTask(@PathVariable long id) {
        return taskService.getById(id);
    }

    @GetMapping("/{id}/file")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable long id) {
        Resource file = taskService.loadFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteTask(@PathVariable long id) {
        taskService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void doneTask(@PathVariable long id) {
        taskService.done(id);
    }
}
