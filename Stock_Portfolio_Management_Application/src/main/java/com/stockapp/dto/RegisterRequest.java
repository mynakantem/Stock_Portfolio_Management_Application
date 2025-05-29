package com.stockapp.dto;

import com.stockapp.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username is required!")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "Username must be Alphanumeric with underscores only")
    private String username;

    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid Email format! Enter Valid Email")
    private String email;

    @NotBlank(message = "Password is required!")
    // Uncomment if you want strong password validation:
    // @Size(min = 7, max = 20, message = "Password must be between 7 and 20 characters")
    // @Pattern(
    //     regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,}$",
    //     message = "Password should contain uppercase, lowercase, a number, and special character"
    // )
    private String password;
    private Role role;

}