package com.dyherd.jwtdemo.controller;

import com.dyherd.jwtdemo.model.JwtRequest;
import com.dyherd.jwtdemo.model.JwtResponse;
import com.dyherd.jwtdemo.model.User;
import com.dyherd.jwtdemo.service.JwtService;
import com.dyherd.jwtdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/")
public class JwtController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("authenticate")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        return jwtService.getJwtToken(jwtRequest);
    }

    @PostMapping("create")
    public JwtResponse createJwtToken(@RequestBody User user) throws Exception {
        jwtService.create(user);
        JwtRequest jwtRequest = new JwtRequest(user.getUserName(), user.getPassword());
        return jwtService.getJwtToken(jwtRequest);
    }

    @GetMapping("hello")
    public String hello() {
        return "Okay";
    }


    private void authenticate(String username, String userPassword) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userPassword));
        } catch (BadCredentialsException be) {
            throw new RuntimeException("Invalid credentials", be);
        }
    }


}

