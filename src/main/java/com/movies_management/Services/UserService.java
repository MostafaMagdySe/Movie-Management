package com.movies_management.Services;

import com.movies_management.DTO.CreateUserRequest;
import com.movies_management.DTO.UsernameRequest;
import com.movies_management.DTO.loginRequest;
import com.movies_management.Entities.Users;
import com.movies_management.Repository.UserRepo;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
public class UserService {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    private final UserRepo userRepo;


    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepo userRepo, JWTService jwtService, AuthenticationManager authenticationManager)
    {
        this.userRepo= userRepo;
        this.jwtService=jwtService;
        this.authenticationManager=authenticationManager;

    }
    @Transactional
    public void CreateUser (CreateUserRequest createUserRequest){
        Users user = new Users() ;
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(encoder.encode(createUserRequest.getPassword()));
        user.setEmail(createUserRequest.getEmail());
        user.setPhone(createUserRequest.getPhone());
        userRepo.save(user);



    }
    public String verify(loginRequest loginrequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginrequest.getUsername(), loginrequest.getPassword()));

            return jwtService.generateToken(loginrequest.getUsername());}
        catch (Exception e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
    @Transactional
    public void updateUserProfile (UsernameRequest username){
        Users user;
        user= userRepo.findByusername(username.getUsername());
        user.setEmail(username.getNewEmail());
        user.setUsername(username.getNewUsername());
        user.setPhone(username.getPhone());
        userRepo.save(user);
    }


}

