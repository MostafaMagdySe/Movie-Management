package com.movies_management.Controller;

import com.movies_management.DTO.RatingMovieRequest;

import com.movies_management.Services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<String> addRating (@RequestBody RatingMovieRequest ratingMovieRequest){

       if (ratingService.checkifUserRated(ratingMovieRequest)){
           return new ResponseEntity<>("this user already rated this movie",HttpStatus.BAD_REQUEST);
       }
       else if(!ratingService.checkIfMovieExist(ratingMovieRequest.getMovie_id())){
        return new ResponseEntity<>("You CANNOT Rate a Movie that doesn't exist",HttpStatus.BAD_REQUEST);

       }
       else{


ratingService.rateMovie(ratingMovieRequest);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}

    @DeleteMapping("/deleteMovieRating")
    public ResponseEntity<String> deleteRating (@RequestBody RatingMovieRequest ratingMovieRequest){
        if (ratingService.checkifUserRated(ratingMovieRequest)){

        ratingService.deleteRating(ratingMovieRequest);
        return new ResponseEntity<>("Successfully Deleted the Rating",HttpStatus.OK);
    }
        else return new ResponseEntity<>("This User Haven't Rated this Movie yet",HttpStatus.BAD_REQUEST);


    }

}
