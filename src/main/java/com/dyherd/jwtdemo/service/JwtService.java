package com.dyherd.jwtdemo.service;

import com.dyherd.jwtdemo.model.JwtRequest;
import com.dyherd.jwtdemo.model.JwtResponse;
import com.dyherd.jwtdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.dyherd.jwtdemo.model.User user = new com.dyherd.jwtdemo.model.User("dyherd", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6");
        if (user != null) {
            return new User(user.getUserName(), user.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public JwtResponse getJwtToken(JwtRequest jwtRequest) {
        UserDetails userDetails = loadUserByUsername(jwtRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        com.dyherd.jwtdemo.model.User user = new com.dyherd.jwtdemo.model.User(jwtRequest.getUsername(), jwtRequest.getPassword());
        return new JwtResponse(user, token);
    }

    public com.dyherd.jwtdemo.model.User create(com.dyherd.jwtdemo.model.User user) {
        return user;
    }
}
