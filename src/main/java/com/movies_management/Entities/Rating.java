package com.movies_management.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Min(1)
    @Max(10)
    private int rating;

    private int user_id;
    private int movie_id;
    private String comment;


}
