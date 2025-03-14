package com.movies_management.DTO;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class updateProfileRequest {
    @NotNull
    private String username;
     @Email
    private String newEmail;

    private String phone;

    private String newUsername;

}

