package com.movies_management.Entities;

import jakarta.persistence.*;
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
@Column(name = "movie_id")
    private int movieId;
    private String comment;


}
