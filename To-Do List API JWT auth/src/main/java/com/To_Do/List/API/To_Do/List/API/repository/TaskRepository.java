package com.To_Do.List.API.To_Do.List.API.repository;

import com.To_Do.List.API.To_Do.List.API.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findByUserId(int userId);

}
