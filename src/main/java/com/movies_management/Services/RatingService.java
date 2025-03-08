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
@Transactional
    public void rateMovie(RatingMovieRequest ratingMovieRequest){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserRoles userRoles = (UserRoles) auth.getPrincipal();
        String username = userRoles.getUsername();

        Users user = userRepo.findByusername(username);
        Rating ratingEntity = new Rating();
        ratingEntity.setUser_id(user.getId());
        ratingEntity.setRating(ratingMovieRequest.getRating());
        ratingEntity.setComment(ratingMovieRequest.getComment());
        ratingEntity.setMovie_id(ratingMovieRequest.getMovie_id());

        ratingRepo.save(ratingEntity);

    }


}
