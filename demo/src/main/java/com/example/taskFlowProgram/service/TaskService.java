
package com.example.taskFlowProgram.service;

import com.example.taskFlowProgram.DTO.TaskRequest;
import com.example.taskFlowProgram.DTO.TaskResponse;
import com.example.taskFlowProgram.Exception.InvalidTaskException;
import com.example.taskFlowProgram.Exception.TaskNotFoundException;
import com.example.taskFlowProgram.Model.Task;
import com.example.taskFlowProgram.Repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repo;
    ModelMapper modelMapper=new ModelMapper();

    public String createTask(TaskRequest taskRequest)
    {
        if (taskRequest.getTitle() == null || taskRequest.getTitle().isBlank()) {
            throw new InvalidTaskException("Title cannot be empty");
        }
        else {
            Task task = new Task();
            task.setTitle(taskRequest.getTitle());
            task.setDescription(taskRequest.getDescription());
            task.setCompleted(Boolean.parseBoolean(taskRequest.getDescription()));
            repo.save(task);
            return "Data Written successfully";
        }
    }

    public List<TaskResponse> getAll() {
        return repo.findAll().stream().map(x->modelMapper.map(x,TaskResponse.class)).collect(Collectors.toList());
    }

    public Task getById(String id)
    {

        Task task = repo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));

        return task;
    }

    public List<TaskResponse> update(String id, TaskRequest taskRequest) {

        if (taskRequest.getTitle() == null || taskRequest.getTitle().isBlank()) {
            throw new InvalidTaskException("Title cannot be empty");
        }

        Task existing = repo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        Task t = getById(id);
        t.setTitle(taskRequest.getTitle());
        t.setDescription(taskRequest.getDescription());
        t.setCompleted(taskRequest.isCompleted());
        repo.save(t);
        return repo.findById(id).
                stream()
                .map(x->modelMapper.map(x,TaskResponse.class))
                .collect(Collectors.toList());
    }



    public String delete(String id) {
        if (!repo.existsById(id)) {
            throw new TaskNotFoundException("Cannot delete — task not found");
        }
        else {
            repo.deleteById(id);
            return "Delted sucessfully";
        }
    }
}
