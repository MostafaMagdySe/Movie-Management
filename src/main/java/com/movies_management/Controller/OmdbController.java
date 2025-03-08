package com.movies_management.Controller;




import com.movies_management.DTO.OmdbApi.MainRequestOfOmdb;
import com.movies_management.Services.OmdbApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OmdbController {
    private final OmdbApiService omdbApiService;

    public OmdbController(OmdbApiService omdbApiService) {
        this.omdbApiService = omdbApiService;


    }

   @GetMapping("/movieDetails/{title}")
  public MainRequestOfOmdb getMovieDetails(@PathVariable String title) {
      return omdbApiService.getMovieDetails(title);


    }
}
