package com.stockapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.stockapp.dto.AuthRequest;
import com.stockapp.dto.AuthResponse;
import com.stockapp.dto.RegisterRequest;
import com.stockapp.model.User;
import com.stockapp.repository.UserRepository;
import com.stockapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    public UserService userservice;

    @Autowired
    public UserRepository userrepo;

   
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest regreq) {
        AuthResponse response = userservice.register(regreq);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody AuthRequest authreq) {
        AuthResponse response = userservice.login(authreq);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
  
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userrepo.findAll();
        return ResponseEntity.ok(users);
    }

  
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserByName(@PathVariable String username) {
        Optional<User> user = userrepo.findByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.ok("User not found");
        }
    }
    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody RegisterRequest updateduser) {
        Optional<User> optuser = userrepo.findById(id);
        if (optuser.isEmpty()) {
            return ResponseEntity.ok("User not found");
        }
        User existingUser = optuser.get();
        existingUser.setUsername(updateduser.getUsername());
        existingUser.setEmail(updateduser.getEmail());
        existingUser.setPassword(userservice.encodePassword(updateduser.getPassword()));
        existingUser.setRole(updateduser.getRole());
        User saved = userrepo.save(existingUser);
        return ResponseEntity.ok(saved);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id, @PathVariable String role) {
    	
        if (!userrepo.existsById(id)) {
            return ResponseEntity.ok("User not found");
        }
        userrepo.deleteById(id);
        return ResponseEntity.ok("User deleted successfully :)");
    }
}
