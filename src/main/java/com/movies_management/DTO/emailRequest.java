package com.movies_management.DTO;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class emailRequest {
    @NotNull @Email
    private String email;


}

