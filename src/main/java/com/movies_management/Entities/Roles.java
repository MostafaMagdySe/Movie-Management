package com.movies_management.Entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@DynamicInsert
public class Roles {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer    id;
    @NotNull
    private String role_id;

}

