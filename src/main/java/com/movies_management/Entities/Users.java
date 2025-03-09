package com.movies_management.Entities;

import jakarta.persistence.*;
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

    @Column(name="role_id")
    private Integer roleId;

}