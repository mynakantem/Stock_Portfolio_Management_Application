package com.stockapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stockapp.dto.AuthRequest;
import com.stockapp.dto.AuthResponse;
import com.stockapp.dto.RegisterRequest;
import com.stockapp.service.UserService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/auth")
public class UserController {
	 private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserService userservice;
	//Post mapping to register the user
	//remember the password while registering!
	 @PostMapping("/register")
	    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest regreq) {
	        logger.info("Received register request for username: {}", regreq.getUsername());
	        AuthResponse response = userservice.register(regreq);
	        return ResponseEntity.ok(response);
	    }
	//use post mapping to login the user
	 
	    @PostMapping("/login")
	    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody AuthRequest authreq) {
	        logger.info("Received login request for username: {}", authreq.getUsername());
	        AuthResponse response = userservice.login(authreq);
	        return ResponseEntity.ok(response);
	    }

}