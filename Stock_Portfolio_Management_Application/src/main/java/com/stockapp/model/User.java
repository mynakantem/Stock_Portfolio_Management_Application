package com.stockapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * This is the User entity. It represents users in the database.
 */
@Data
@Entity
@Table(
    name = "user",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
    }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Username should be unique
    private String username;

    // User email, should also be unique
    private String email;

    // Password is stored in encoded format
    private String password;

    // Role can be USER or ADMIN
    @Enumerated(EnumType.STRING)
    private Role role;
    
}
