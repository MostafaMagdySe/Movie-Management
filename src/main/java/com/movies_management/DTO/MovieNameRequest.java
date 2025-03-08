package com.movies_management.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class MovieNameRequest {

@NotBlank(message = "Movie title cannot be null or empty")
private String title;


}
