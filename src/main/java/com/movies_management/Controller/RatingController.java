package com.movies_management.Controller;

import com.movies_management.DTO.MovieReviewResponse;
import com.movies_management.DTO.RatingMovieResponse;

import com.movies_management.Services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RatingController {
private final RatingService ratingService;


public RatingController(RatingService ratingService){
    this.ratingService=ratingService;


}


    @PostMapping("/rateMovie")
    public ResponseEntity<String> addRating (@RequestBody RatingMovieResponse ratingMovieResponse){

       if (ratingService.checkIfUserRated(ratingMovieResponse)){
           return new ResponseEntity<>("this user already rated this movie",HttpStatus.BAD_REQUEST);
       }
       else if(!ratingService.checkIfMovieExist(ratingMovieResponse.getMovie_id())){
        return new ResponseEntity<>("You CANNOT Rate a Movie that doesn't exist",HttpStatus.BAD_REQUEST);

       }
       else{


ratingService.rateMovie(ratingMovieResponse);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}

    @DeleteMapping("/deleteMovieRating")
    public ResponseEntity<String> deleteRating (@RequestBody RatingMovieResponse ratingMovieResponse){
        if (ratingService.checkIfUserRated(ratingMovieResponse)){

        ratingService.deleteRating(ratingMovieResponse);
        return new ResponseEntity<>("Successfully Deleted the Rating",HttpStatus.OK);
    }
        else return new ResponseEntity<>("This User Haven't Rated this Movie yet",HttpStatus.BAD_REQUEST);


    }

    @GetMapping("/Rating/{movieId}")
    public ResponseEntity<List<MovieReviewResponse>> Rating(@PathVariable int movieId){
    if(ratingService.checkIfMovieExist(movieId)){

        List<MovieReviewResponse> review=  ratingService.getAllReviews(movieId);


        return new ResponseEntity<>(review,HttpStatus.OK);
    }
else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}


}
