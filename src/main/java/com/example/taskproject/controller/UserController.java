package com.example.taskproject.controller;

import com.example.taskproject.entity.Users;
import com.example.taskproject.payload.JwtAuthenticationResponse;
import com.example.taskproject.payload.LoginDto;
import com.example.taskproject.payload.UserDto;
import com.example.taskproject.security.JwtProvider;
import com.example.taskproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class UserController {
@Autowired
    private UserService userService;
@Autowired
    JwtProvider jwtProvider;
@Autowired
private AuthenticationManager authenticationManager;
    //Creating User
    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
            return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> loginUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );
        System.out.println(authentication);


        SecurityContextHolder.getContext().setAuthentication(authentication);
       String token = jwtProvider.generateToken(authentication);
          System.out.println(token+"Ravi");
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        //return new ResponseEntity<>("User Logged",HttpStatus.OK);
    }

}
