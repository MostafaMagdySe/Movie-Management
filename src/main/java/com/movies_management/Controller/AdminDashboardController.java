package com.movies_management.Controller;

import com.movies_management.DTO.MovieNameRequest;
import com.movies_management.DTO.OmdbApi.MainRequestOfOmdb;
import com.movies_management.Repository.MoviesInsideWebsiteRepo;
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

    public AdminDashboardController(MovieService movieService){
        this.movieService=movieService;


    }

@PostMapping ("/addmovie")
    public  ResponseEntity addMovies (@Valid @RequestBody MovieNameRequest movie){
    MainRequestOfOmdb omdbtodb= movieService.gettingMovieDetails(movie);
if (!movieService.checkIfMovieExist(omdbtodb,movie)) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
else{

    movieService.addMovies(omdbtodb,movie);
return new ResponseEntity<>(HttpStatus.OK);}

}
@DeleteMapping("/deleteMovie")
public ResponseEntity deleteMovies(@RequestBody MovieNameRequest movie){

movieService.deleteMovies(movie.getTitle());

    return new ResponseEntity<>(HttpStatus.OK);}
    }








