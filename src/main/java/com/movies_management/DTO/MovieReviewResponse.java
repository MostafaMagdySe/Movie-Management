package com.movies_management.DTO;

import lombok.Data;


@Data
public class MovieReviewResponse {

    private String username;
    private int rating;
    private String comment;

}
