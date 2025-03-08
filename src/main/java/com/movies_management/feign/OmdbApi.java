package com.movies_management.feign;



import com.movies_management.DTO.OmdbApi.MainRequestOfOmdb;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "OmdbApi",
        url = "http://www.omdbapi.com/")
public interface OmdbApi {


    @GetMapping("//")
    MainRequestOfOmdb getMovieDetails(@RequestParam("apikey") String apiKey,
                                      @RequestParam("t") String title);



}




