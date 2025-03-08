package com.movies_management.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert

@Data

public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int    id;
    @NotNull
    private String username;

    private String email;
    @NotNull
    private String password;

    private String phone;

    private Integer role_id;

}