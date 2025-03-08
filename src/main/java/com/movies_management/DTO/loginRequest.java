package com.movies_management.DTO;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class loginRequest {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}

