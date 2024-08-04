package com.To_Do.List.API.To_Do.List.API.service;

import com.To_Do.List.API.To_Do.List.API.exception.ResourceNotFoundException;
import com.To_Do.List.API.To_Do.List.API.model.Task;
import com.To_Do.List.API.To_Do.List.API.model.User;
import com.To_Do.List.API.To_Do.List.API.repository.TaskRepository;
import com.To_Do.List.API.To_Do.List.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task createTask(Task task){
        User user = checkUser();
        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public List<Task> getAllTaskByUsername (){
        User user = checkUser();
        return taskRepository.findByUserId(user.getId());
    }

    public void changeStatus(int id){

        User user = checkUser();
        Task task = taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Task not found"));

        if(task.getUser().getId() != user.getId()){
            throw new SecurityException("You dont have permission to change status");
        }
        task.setDone(!task.isDone());
        taskRepository.save(task);
    }

    public void updateTask(int id,Task task){
        if(!taskRepository.existsById(id)){
            throw new ResourceNotFoundException("Task not found with ID " + id);
        }
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Task not found with ID "+ id));
        User user = checkUser();
        existingTask.setDescription(task.getDescription());
        existingTask.setUser(user);
        existingTask.setDone(task.isDone());
        existingTask.setCreatedAt(LocalDateTime.now());

        taskRepository.save(existingTask);
    }
    public void deleteTask(int id){
        taskRepository.deleteById(id);
    }

    private User checkUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return user;
    }
}
