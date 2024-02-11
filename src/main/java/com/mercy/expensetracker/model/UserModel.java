package com.mercy.expensetracker.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {
    @NotBlank(message = "Fullname cannot be null")
    private String fullName;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password cannot be null")
    @Size(min = 5, message = "Password should be at least 5 characters")
    private String password;
}
