package com.movies_management.DTO;



import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class CreateUserResponse {
    private int id;
    @NotNull
    private String username;
    private String email;
    @NotNull
    private String password;
    private String phone;
    private int  role_id;

}

