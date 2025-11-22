package com.example.taskFlowProgram.Controller;

import com.example.taskFlowProgram.DTO.TaskRequest;
import com.example.taskFlowProgram.DTO.TaskResponse;
import com.example.taskFlowProgram.Model.Task;
import com.example.taskFlowProgram.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping
    public String create(@RequestBody TaskRequest taskRequest) { return service.createTask(taskRequest); }

    @GetMapping
    public List<TaskResponse> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Task getById(@PathVariable String id) { return service.getById(id); }

    @PutMapping("/{id}")
    public List<TaskResponse> update(@PathVariable String id, @RequestBody TaskRequest taskRequest) {
        return service.update(id, taskRequest);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) { return service.delete(id); }
}
