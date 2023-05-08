package com.example.taskproject.service;

import com.example.taskproject.payload.UserDto;
import org.springframework.stereotype.Service;


public interface UserService {
    public UserDto createUser(UserDto userDto);


}
