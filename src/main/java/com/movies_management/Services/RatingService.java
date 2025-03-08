package com.movies_management.Services;

import com.movies_management.Config.UserRoles;
import com.movies_management.DTO.RatingMovieRequest;
import com.movies_management.Entities.Rating;
import com.movies_management.Entities.Users;
import com.movies_management.Repository.RatingRepo;
import com.movies_management.Repository.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatingService {
    private final UserRepo userRepo;
    private final RatingRepo ratingRepo;
    public RatingService(UserRepo userRepo,RatingRepo ratingRepo){
        this.userRepo=userRepo;
        this.ratingRepo=ratingRepo;

    }


    public Users getUserInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserRoles userRoles = (UserRoles) auth.getPrincipal();
        String username = userRoles.getUsername();
        return userRepo.findByusername(username);

    }
@Transactional
    public void rateMovie(RatingMovieRequest ratingMovieRequest){
        Users user= getUserInfo();

        Rating ratingEntity = new Rating();
        ratingEntity.setUser_id(user.getId());
        ratingEntity.setRating(ratingMovieRequest.getRating());
        ratingEntity.setComment(ratingMovieRequest.getComment());
        ratingEntity.setMovie_id(ratingMovieRequest.getMovie_id());

        ratingRepo.save(ratingEntity);

    }
    public Boolean checkifUserRated(RatingMovieRequest ratingMovieRequest){
        Users user =getUserInfo();
        return ratingRepo.existsByUserIdAndMovieId(user.getId(), ratingMovieRequest.getMovie_id());



    }

    public void deleteRating(RatingMovieRequest ratingMovieRequest){
        if (checkifUserRated(ratingMovieRequest)){
            Users user =getUserInfo();
     Rating ratingEntity = ratingRepo.findByUserIdAndMovieId(user.getId(),ratingMovieRequest.getMovie_id() );
            ratingRepo.delete(ratingEntity);
        }

    }


}
