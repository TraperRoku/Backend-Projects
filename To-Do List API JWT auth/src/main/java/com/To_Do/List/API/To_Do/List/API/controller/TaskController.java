package com.To_Do.List.API.To_Do.List.API.controller;


import com.To_Do.List.API.To_Do.List.API.exception.ResourceNotFoundException;
import com.To_Do.List.API.To_Do.List.API.model.Task;
import com.To_Do.List.API.To_Do.List.API.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
   private TaskService taskService;

    @GetMapping("/user")
    public List<Task> getTaskByUserId(){
        return taskService.getAllTaskByUsername();
    }

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task){
     return taskService.createTask(task);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTask(@PathVariable int id,@RequestBody Task task){
        try {
            taskService.updateTask(id, task);
            return new ResponseEntity<>("Task updated",HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (SecurityException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
        }
        }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id){
        try {
            taskService.deleteTask(id);
            return new ResponseEntity<>("Task deleted",HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch(SecurityException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("status/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable int id){
        try {
            taskService.changeStatus(id);
            return new ResponseEntity<>("Task changed", HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch(SecurityException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
        }
    }


}
