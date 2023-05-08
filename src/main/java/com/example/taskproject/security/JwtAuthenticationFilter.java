package com.example.taskproject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
   public JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetails customUserDetails;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get the token from headers
        //check the token valid or not
        // load the user and setAuthentication
        String token = getToken(request);
        if(StringUtils.hasText(token) && jwtProvider.validateToken(token)){
            String email = jwtProvider.getEmailFromToken(token);
            UserDetails userDetails = customUserDetails.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);


    }

    private  String getToken(HttpServletRequest request){
        String token =request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")){
             return token.substring(7,token.length());
        }
        return null;


    }
}
