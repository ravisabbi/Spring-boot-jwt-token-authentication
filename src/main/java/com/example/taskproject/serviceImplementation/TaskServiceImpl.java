package com.example.taskproject.serviceImplementation;

import com.example.taskproject.entity.Task;
import com.example.taskproject.entity.Users;
import com.example.taskproject.exception.APIException;
import com.example.taskproject.exception.TaskNotFound;
import com.example.taskproject.exception.UserNotFound;
import com.example.taskproject.payload.TaskDto;
import com.example.taskproject.repository.TaskRepository;
import com.example.taskproject.repository.UserRepository;
import com.example.taskproject.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class TaskServiceImpl implements TaskService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskDto saveTask(long userId, TaskDto taskDto) {
       Users user = userRepository.findById(userId).orElseThrow(
               ()-> new UserNotFound(String.format("User Id %d is not found",userId))
       );
        Task task = modelMapper.map(taskDto, Task.class);
        task.setUsers(user); // After setting user we are storing in db
        Task savedTask = taskRepository.save(task);
        return  modelMapper.map(savedTask,TaskDto.class);


    }

    @Override
    public List<TaskDto> getAllTasks(long userId) {
          userRepository.findById(userId).orElseThrow(
                ()-> new UserNotFound(String.format("User Id %d is not found",userId))
        );

          List<Task> tasks = taskRepository.findAllByUsers_Id(userId);

        return  tasks.stream().map(task -> modelMapper.map(task,TaskDto.class)).collect(Collectors.toList());
    }

    @Override
    public TaskDto getTask(long userId, long taskId) {

       Users users =  userRepository.findById(userId).orElseThrow(
                ()-> new UserNotFound(String.format("User Id %d is not found",userId))
        );

       Task task = taskRepository.findById(taskId).orElseThrow(
               ()-> new TaskNotFound(String.format("Task id %d not found",taskId))
       );

     if(users.getId() != task.getUsers().getId()){
         throw new APIException( "Task Id  not belongs User Id");

     }
        return modelMapper.map(task,TaskDto.class);
    }

    @Override
    public void deleteTask(long userId, long taskId) {

        Users users =  userRepository.findById(userId).orElseThrow(
                ()-> new UserNotFound(String.format("User Id %d is not found",userId))
        );

        Task task = taskRepository.findById(taskId).orElseThrow(
                ()-> new TaskNotFound(String.format("Task id %d not found",taskId))
        );

        if(users.getId() != task.getUsers().getId()){
            throw new APIException( "Task Id  not belongs User Id");

        }
     taskRepository.deleteById(taskId);//Delete task



    }


}
