package com.example.taskproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taskproject.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByUsers_Id(long userId);
}
