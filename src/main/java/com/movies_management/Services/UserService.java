package com.movies_management.Services;

import com.movies_management.DTO.CreateUserResponse;
import com.movies_management.DTO.UsernameRequest;
import com.movies_management.DTO.LoginRequest;
import com.movies_management.Entities.Users;
import com.movies_management.Repository.UserRepo;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
public class UserService {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    private final UserRepo userRepo;
    private final CustomUserDetailsService customUserDetailsService;


    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepo userRepo, JWTService jwtService, AuthenticationManager authenticationManager,CustomUserDetailsService customUserDetailsService)
    {
        this.userRepo= userRepo;
        this.jwtService=jwtService;
        this.authenticationManager=authenticationManager;
        this.customUserDetailsService=customUserDetailsService;

    }
    @Transactional
    public void CreateUser (CreateUserResponse createUserResponse){
        Users user = new Users() ;
        user.setUsername(createUserResponse.getUsername());
        user.setPassword(encoder.encode(createUserResponse.getPassword()));
        user.setEmail(createUserResponse.getEmail());
        user.setPhone(createUserResponse.getPhone());
        userRepo.save(user);



    }
    public String verify(LoginRequest loginrequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginrequest.getUsername(), loginrequest.getPassword())
            );

            // ✅ Fetch user details after authentication
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginrequest.getUsername());

            // ✅ Generate token using UserDetails (includes roles)
            return jwtService.generateToken(userDetails);
        } catch (Exception e) {
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

