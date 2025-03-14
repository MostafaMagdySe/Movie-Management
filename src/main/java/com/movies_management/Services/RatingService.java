package com.movies_management.Services;

import com.movies_management.Config.UserRoles;
import com.movies_management.DTO.MovieReviewResponse;
import com.movies_management.DTO.RatingMovieResponse;
import com.movies_management.Entities.Rating;
import com.movies_management.Entities.Users;
import com.movies_management.Repository.MoviesInsideWebsiteRepo;
import com.movies_management.Repository.RatingRepo;
import com.movies_management.Repository.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service
public class RatingService {
    private final UserRepo userRepo;
    private final RatingRepo ratingRepo;
    private final MoviesInsideWebsiteRepo movieRepo;
    public RatingService(UserRepo userRepo,RatingRepo ratingRepo,MoviesInsideWebsiteRepo movieRepo){
        this.userRepo=userRepo;
        this.ratingRepo=ratingRepo;
        this.movieRepo=movieRepo;

    }


    public Users getUserInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserRoles userRoles = (UserRoles) auth.getPrincipal();
        String username = userRoles.getUsername();
        return userRepo.findByusername(username);

    }
@Transactional
    public void rateMovie(RatingMovieResponse ratingMovieResponse){
        Users user= getUserInfo();

        Rating ratingEntity = new Rating();
        ratingEntity.setUser_id(user.getId());
        ratingEntity.setRating(ratingMovieResponse.getRating());
        ratingEntity.setComment(ratingMovieResponse.getComment());
        ratingEntity.setMovieId(ratingMovieResponse.getMovie_id());

        ratingRepo.save(ratingEntity);

    }
    public boolean checkifUserRated(RatingMovieResponse ratingMovieResponse){
        Users user =getUserInfo();
        return ratingRepo.existsByUserIdAndMovieId(user.getId(), ratingMovieResponse.getMovie_id());



    }

    public void deleteRating(RatingMovieResponse ratingMovieResponse){
        if (checkifUserRated(ratingMovieResponse)){
            Users user =getUserInfo();
     Rating ratingEntity = ratingRepo.findByUserIdAndMovieId(user.getId(), ratingMovieResponse.getMovie_id() );
            ratingRepo.delete(ratingEntity);
        }

    }

    public boolean checkIfMovieExist(int id){
        return movieRepo.existsById(id);

    }

public  List<MovieReviewResponse> getAllReviews(int movieId){
    List<Rating> rating =ratingRepo.findByMovieId(movieId);

    List<MovieReviewResponse> review = new ArrayList<>();


    for (int i=0; i<rating.size();i++ ){
        MovieReviewResponse movieReview = new MovieReviewResponse();
        movieReview.setUsername(userRepo.findById(rating.get(i).getUser_id()).getUsername());
        movieReview.setComment(rating.get(i).getComment());
        movieReview.setRating(rating.get(i).getRating());
        review.add(movieReview);


    }

       return review;

}





}
