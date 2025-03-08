package com.movies_management.Controller;

import com.movies_management.DTO.ListOfMovieRequest;

import com.movies_management.DTO.OmdbApi.MainRequestOfOmdb;

import com.movies_management.Services.MovieService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminDashboardController {
    private final MovieService movieService;

    public AdminDashboardController(MovieService movieService) {
        this.movieService = movieService;


    }

    @PostMapping("/addmovie")
    public ResponseEntity<String> addMovies( @Valid @RequestBody ListOfMovieRequest movieList) {
        StringBuilder responseMessage = new StringBuilder();
        for (String i : movieList.getTitles()) {
            if(!movieService.checkIfMovieExistInDB(i)){
            MainRequestOfOmdb omdbtodb = movieService.gettingMovieDetails(i);
            if (!movieService.checkIfMovieExistInOmdb(omdbtodb)) {
                responseMessage.append("Movie \"").append(i).append("\" isn't Found on Omdb Api").append("\n");
            } else {

                movieService.addMovies(omdbtodb);
                responseMessage.append("Successfully added Movie: \"").append(i).append("\"\n");
            }
        }else responseMessage.append("Movie \"").append(i).append("\" is already Stored in the Database").append("\n");




        }

        return new ResponseEntity<>(responseMessage.toString(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteMovie")
    public ResponseEntity<String> deleteMovies(@RequestBody ListOfMovieRequest movieList) {
        StringBuilder responseMessage = new StringBuilder();
        for (String i : movieList.getTitles()) {
            if(movieService.checkIfMovieExistInDB(i)){
            movieService.deleteMovies(i);
                responseMessage.append("Successfully deleted Movie: \"").append(i).append("\"\n");
            }
            else responseMessage.append("Failed to delete Movie: \"").append(i).append("\" Because it Doesn't Exist \n");


        }
        return new ResponseEntity<>(responseMessage.toString(), HttpStatus.OK);

    }


}



