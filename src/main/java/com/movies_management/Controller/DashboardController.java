package com.movies_management.Controller;


import com.movies_management.DTO.EmailResponse;
import com.movies_management.DTO.UserNameResponse;
import com.movies_management.DTO.updateProfileRequest;
import com.movies_management.DTO.LoginRequest;
import com.movies_management.Entities.MovieInfo;
import com.movies_management.Entities.Users;
import com.movies_management.Services.DashboardService;
import com.movies_management.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserService userService;

    public DashboardController(DashboardService dashboardService,UserService userService){
this.userService=userService;
        this.dashboardService=dashboardService;
    }


    @GetMapping("/")
    public ResponseEntity <List<MovieInfo>> getMovies(@RequestParam(defaultValue = "1") int PageNo,
                                                      @RequestParam(defaultValue = "5") int PageSize){

        List<MovieInfo> movies=dashboardService.fetchAllMoviesFromDB(PageNo-1,PageSize);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieInfo>> searchMovies(@RequestParam String keyword){

        List<MovieInfo> movies=dashboardService.searchMovies(keyword);
        if (movies.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        return new ResponseEntity<>(movies, HttpStatus.OK);

    }
    @GetMapping("/Movies/{name}")
public ResponseEntity<Map<String, Object>> viewMovie(@PathVariable String name){

        Map<String, Object> movieMap=  dashboardService.viewMovie(name);
        if (movieMap.containsKey("message")) {
            return new ResponseEntity<>(movieMap, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(movieMap, HttpStatus.OK);

    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userService.verify(loginRequest);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/updateProfile")
    public ResponseEntity updateProfile(@RequestBody @Valid updateProfileRequest username){

       if( userService.updateUserProfile(username)){

        return new ResponseEntity<>(HttpStatus.OK);
    }
    else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/userProfile")
    public ResponseEntity<Users> getProfile(@RequestBody UserNameResponse username){
Users user=userService.getUserProfile(username.getUsername());

        return new ResponseEntity<>(user,HttpStatus.OK);

}
}
