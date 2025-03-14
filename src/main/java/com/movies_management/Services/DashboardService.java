package com.movies_management.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies_management.Entities.MovieInfo;
import com.movies_management.Entities.Movies;
import com.movies_management.Repository.MoviesInsideWebsiteRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service

public class DashboardService {
private final MoviesInsideWebsiteRepo movieRepo;
     DashboardService(MoviesInsideWebsiteRepo movieRepo){
          this.movieRepo=movieRepo;
     }


     public List<MovieInfo> fetchAllMoviesFromDB(int PageNo, int PageSize){
          Pageable pageable =  PageRequest.of(PageNo,PageSize, Sort.by("id"));
return movieRepo.findAllByMovieInfo(pageable);


     }

     public List<MovieInfo> searchMovies (String keyword){
         return  movieRepo.searchMovies(keyword);


     }
     public Map<String, Object> viewMovie (String name){

          String decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
          Movies movieName = movieRepo.findByTitle(decodedName);
          if(movieName==null){return Map.of("message", "Movie is not in the Website Yet!");}
          ObjectMapper objectMapper = new ObjectMapper();
          Map<String, Object> movieMap = objectMapper.convertValue(movieName, new TypeReference<>() {});


          movieMap.values().removeIf(Objects::isNull);

          return movieMap;
     }










}
