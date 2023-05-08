package com.example.taskproject.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private long Id;
    private String name;
    private String email;
    private  String password;

}
