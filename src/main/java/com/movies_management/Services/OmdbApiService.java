package com.movies_management.Services;



import com.movies_management.DTO.OmdbApi.MainResponseOfOmdb;

import com.movies_management.feign.OmdbApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service

public class OmdbApiService {
    @Value("${omdb.key}")
    private String apikey;
    private final OmdbApi omdbApi;

    public OmdbApiService(OmdbApi omdbApi) {

        this.omdbApi=omdbApi;
    }

    public MainResponseOfOmdb getMovieDetails(String title) {
        return omdbApi.getMovieDetails(apikey,title);


    }
}
