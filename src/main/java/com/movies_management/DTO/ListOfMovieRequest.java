package com.movies_management.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ListOfMovieRequest {


    @NotEmpty(message = "Movie title cannot be empty")
    @Size(max = 10, message = "You can add a maximum of 10 movies at once")
    private List<@NotBlank(message = "Movie title cannot be blank")String> titles;




}
