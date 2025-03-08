package com.movies_management.Repository;

import com.movies_management.Entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepo extends JpaRepository<Rating,Integer> {

    @Query("SELECT r FROM Rating r WHERE r.user_id = :userId AND r.movie_id = :movieId")
    Rating findByUserIdAndMovieId(@Param("userId") int userId, @Param("movieId") int movieId);




    @Query("SELECT COUNT(r) > 0 FROM Rating r WHERE r.user_id = :userId AND r.movie_id = :movieId")
    boolean existsByUserIdAndMovieId(@Param("userId") int userId, @Param("movieId") int movieId);



}
