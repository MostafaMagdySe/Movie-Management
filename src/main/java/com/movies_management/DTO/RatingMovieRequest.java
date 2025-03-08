package com.movies_management.DTO;

import lombok.Data;

@Data
public class RatingMovieRequest {
    private int rating;
    private String comment;
    private int movie_id;



}
