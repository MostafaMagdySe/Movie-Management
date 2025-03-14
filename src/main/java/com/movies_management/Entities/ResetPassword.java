package com.movies_management.Entities;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Data
@Table(name = "resetpassword")
public class ResetPassword {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String email;
    private String code;
    @CreationTimestamp
    private Instant createdat;



}

