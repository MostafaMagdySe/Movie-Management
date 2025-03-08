package com.movies_management.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Movies {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String Title,Year,Rated,Released,Runtime,Genre,Director,
            Writer,Actors,plot,Language,Country,Awards,Poster,
            Metascore,Type,BoxOffice,imdbid,imdb_votes,imdb_rating,
            rotten_tomatoes,rotten_tomatoes_rating,metacritic,
            metacritic_rating;



    private Boolean Response;





}
