package com.example.taskproject.service;

import com.example.taskproject.payload.TaskDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskService {
    public  TaskDto saveTask(long userId, TaskDto taskDto);
    public List<TaskDto> getAllTasks(long userId);
    public TaskDto getTask(long userId,long taskId);
    public void deleteTask(long userId,long taskId);
}
