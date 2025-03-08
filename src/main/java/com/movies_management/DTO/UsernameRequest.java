package com.movies_management.DTO;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsernameRequest {
    @NotNull
    private String username;
    @NotNull @Email
    private String newEmail;
    @NotNull
    private String phone;
    @NotNull
    private String newUsername;

}

