package com.example.taskproject.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collector;

@Setter
@Getter
public class TaskDto {
    private long id;
    private String taskname;


}
