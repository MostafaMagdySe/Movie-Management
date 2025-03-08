package com.movies_management.Repository;

import com.movies_management.Entities.MovieInfo;
import com.movies_management.Entities.Movies;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesInsideWebsiteRepo extends JpaRepository<Movies,Integer> {

    @Query("SELECT m FROM Movies m WHERE LOWER(m.Title) LIKE LOWER (CONCAT('%', :keyword,'%'))" )
    List<MovieInfo> searchMovies(String keyword);

    @Query("SELECT m.Title AS title, m.Poster AS poster FROM Movies m")
    List<MovieInfo> findAllByMovieInfo(Pageable pageable);


    @Query("SELECT m FROM Movies m WHERE m.Title = :title")
    Movies findByTitle(@Param("title") String title);
}
