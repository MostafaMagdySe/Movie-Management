package com.movies_management.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ListOfMovieRequest {


    @NotEmpty(message = "Movie title cannot be empty")
    private List<@NotBlank(message = "Movie title cannot be blank")String> titles;




}
