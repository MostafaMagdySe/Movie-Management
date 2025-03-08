package com.movies_management.DTO.OmdbApi;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class) // Automatically maps UpperCamelCase JSON keys

public class RatingDTO {


    private String Source;
    private String Value;
}
