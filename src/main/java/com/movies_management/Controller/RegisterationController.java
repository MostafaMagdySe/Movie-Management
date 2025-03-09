package com.movies_management.Controller;



import com.movies_management.DTO.CreateUserResponse;
import com.movies_management.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class RegisterationController {

    private final UserService userService;

    public RegisterationController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity CreateUsers (@RequestBody CreateUserResponse createUserResponse){


        userService.CreateUser(createUserResponse);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}

