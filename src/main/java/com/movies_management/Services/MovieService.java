package com.movies_management.Services;

import com.movies_management.DTO.MovieNameRequest;
import com.movies_management.DTO.OmdbApi.MainRequestOfOmdb;
import com.movies_management.DTO.OmdbApi.RatingDTO;
import com.movies_management.Entities.Movies;
import com.movies_management.Repository.MoviesInsideWebsiteRepo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;


@Service
public class MovieService {

    private final MoviesInsideWebsiteRepo  movierepo;
    private final OmdbApiService omdbApiService;

    MovieService(MoviesInsideWebsiteRepo  movierepo,OmdbApiService omdbApiService){
        this.movierepo=movierepo;
        this.omdbApiService=omdbApiService;


    }
    // resposible for getting movie details from the omdbApi and save it to omdbtodb object
    public  MainRequestOfOmdb gettingMovieDetails(MovieNameRequest movie){
     MainRequestOfOmdb omdbtodb= omdbApiService.getMovieDetails(movie.getTitle());
    return omdbtodb;
    }

    // Checks if movie exists in omdbApi in order not to save bad movie details in database
     public Boolean checkIfMovieExist(MainRequestOfOmdb omdbtodb,MovieNameRequest movie){

         if (omdbtodb== null || omdbtodb.getTitle()== null || omdbtodb.getYear()==null|| omdbtodb.getResponse()!=true ){return false;}
         return true;

     }
    @Transactional
    public void replaceNullValues(Movies movie) {
        Field[] fields = Movies.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(movie) == null) {
                        field.set(movie, "N/A");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }}}}








    // this method is responsible for getting movie's details from the omdb Api and then save the details to database
    @Transactional
public void addMovies(MainRequestOfOmdb omdbtodb,MovieNameRequest movie){
    Movies movieEntity = new Movies();

    movieEntity.setTitle(omdbtodb.getTitle());
    movieEntity.setGenre(omdbtodb.getGenre());
    movieEntity.setAwards(omdbtodb.getAwards());
    movieEntity.setActors(omdbtodb.getActors());
    movieEntity.setCountry(omdbtodb.getCountry());
    movieEntity.setDirector(omdbtodb.getDirector());
    movieEntity.setPlot(omdbtodb.getPlot());
    movieEntity.setLanguage(omdbtodb.getLanguage());
    movieEntity.setBoxOffice(omdbtodb.getBoxOffice());
    movieEntity.setPoster(omdbtodb.getPoster());
    movieEntity.setResponse(omdbtodb.getResponse());
    movieEntity.setRated(omdbtodb.getRated());
    movieEntity.setYear(omdbtodb.getYear());
    movieEntity.setReleased(omdbtodb.getReleased());
    movieEntity.setRuntime(omdbtodb.getRuntime());
    movieEntity.setWriter(omdbtodb.getWriter());
    movieEntity.setMetascore(omdbtodb.getMetascore());
    movieEntity.setType(omdbtodb.getType());
    movieEntity.setImdbid(omdbtodb.getImdbID());
    movieEntity.setImdb_rating(omdbtodb.getImdbRating());
    movieEntity.setImdb_votes(omdbtodb.getImdbVotes());
// Obtaining data from the list can be done using a for loop, but since the response is known to be 3 combinations. no need to increase the complexity
    List<RatingDTO> ratings = omdbtodb.getRatings();
    if (ratings != null && ratings.size() >= 2) {
    movieEntity.setRotten_tomatoes(ratings.get(1).getSource());
    movieEntity.setRotten_tomatoes_rating(ratings.get(1).getValue());

    movieEntity.setMetacritic(ratings.get(2).getSource());
    movieEntity.setMetacritic_rating(ratings.get(2).getValue());

    }
    replaceNullValues(movieEntity);
    movierepo.save(movieEntity);

}

 public void deleteMovies (String title){
     Movies movieEntity=  movierepo.findByTitle(title);
        movierepo.delete(movieEntity);


 }






}
