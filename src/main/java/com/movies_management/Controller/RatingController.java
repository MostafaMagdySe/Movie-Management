package com.movies_management.Controller;

import com.movies_management.DTO.RatingMovieRequest;
import com.movies_management.Services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingController {
private final RatingService ratingService;

public RatingController(RatingService ratingService){
    this.ratingService=ratingService;

}


    @PostMapping("/rateMovie")
    public ResponseEntity addRating (@RequestBody RatingMovieRequest ratingMovieRequest){

ratingService.rateMovie(ratingMovieRequest);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
