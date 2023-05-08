package com.example.taskproject.controller;

import com.example.taskproject.payload.TaskDto;
import com.example.taskproject.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    //Save the tasks
    @Autowired
    private TaskService taskService;
    @PostMapping("/{userId}/tasks")
    public ResponseEntity<TaskDto> saveTask(@PathVariable("userId") long userid, @RequestBody TaskDto taskDto){
           return new ResponseEntity<>(taskService.saveTask(userid,taskDto), HttpStatus.CREATED);
    }


    //Get all tasks

    @GetMapping("/{userId}/tasks")
     public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable("userId") long userId){
        return  new ResponseEntity<>(taskService.getAllTasks(userId), HttpStatus.OK);
    }
    @GetMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("userId") long userId, @PathVariable("taskId") long taskId){
              return  new ResponseEntity<>(taskService.getTask(userId,taskId),HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<String>deleteTask(@PathVariable("userId") long userId, @PathVariable("taskId") long taskId){
        taskService.deleteTask(userId,taskId);
        return new ResponseEntity<>("Task Deleted" ,HttpStatus.OK);
    }

}
