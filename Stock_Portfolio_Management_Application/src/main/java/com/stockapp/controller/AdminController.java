package com.stockapp.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.stockapp.dto.AuthRequest;
import com.stockapp.dto.AuthResponse;
import com.stockapp.dto.RegisterRequest;
import com.stockapp.exception.InvalidRoleException;
import com.stockapp.model.User;
import com.stockapp.repository.UserRepository;
import com.stockapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public UserService userservice;

    @Autowired
    public UserRepository userrepo;
  
   
  
  //use get mapping to get all the registered users
    @GetMapping
   
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Admin requested all users");
        List<User> users = userrepo.findAll();
        return ResponseEntity.ok(users);
    }
  //use get mapping to get user by usernames
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserByName(@PathVariable String username) {
        logger.info("Fetching user by username: {}", username);
        Optional<User> user = userrepo.findByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("User not found with username: {}", username);
            return ResponseEntity.ok("User not found");
        }
    }
  //use put mapping to update user by their id
    @PutMapping("/{id}/role")
    public ResponseEntity<User> updateUser(@Valid @PathVariable Long id, @RequestBody RegisterRequest updatedUser) {
        logger.info("Request to update user ID: {}", id);
        User updated = userservice.updateUserById(id, updatedUser);
        return ResponseEntity.ok(updated);
    }


  //use delete mapping to delete users by their id
   
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        logger.info("Delete request received for user with ID: {}", id);

        if (!userrepo.existsById(id)) {
            logger.warn("User not found with ID: {}", id);
            return "User not found";
        }

        userrepo.deleteById(id);
        logger.info("User deleted successfully with ID: {}", id);
        return "User deleted successfully";
    }


}
