package com.example.taskproject.security;

import com.example.taskproject.entity.Users;
import com.example.taskproject.exception.UserNotFound;
import com.example.taskproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetails implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user =  userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFound(String.format("User with email : %s is not found", email))
        );
        Set<String> roles = new HashSet<String>();
        roles.add("ROLE_ADMIN");
        return  new User(user.getEmail(),user.getPassword(),userAuthorities(roles));
    }
    private Collection<? extends GrantedAuthority> userAuthorities(Set<String> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }
}
