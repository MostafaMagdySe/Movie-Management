package com.movies_management.Services;

import com.movies_management.Config.UserRoles;
import com.movies_management.DTO.CreateUserResponse;
import com.movies_management.DTO.updateProfileRequest;
import com.movies_management.DTO.LoginRequest;
import com.movies_management.Entities.Users;
import com.movies_management.Repository.UserRepo;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginrequest.getUsername());


            return jwtService.generateToken(userDetails);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
    public Users getUserInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserRoles userRoles = (UserRoles) auth.getPrincipal();
        String username = userRoles.getUsername();
        return userRepo.findByusername(username);

    }

    @Transactional
    public boolean updateUserProfile (updateProfileRequest username){
        Users currentUser= getUserInfo();
        if (currentUser.getUsername().equals(username.getUsername())){
        Users user= userRepo.findByusername(username.getUsername());
        if(username.getNewEmail()!=null){
        user.setEmail(username.getNewEmail());}
        if(username.getNewUsername()!=null){
        user.setUsername(username.getNewUsername());}
        if(username.getPhone()!=null){
        user.setPhone(username.getPhone());}
        userRepo.save(user);
        return true;
        }
        return false;

    }

    public Users getUserProfile(String username){

        return userRepo.findByusername(username);
    }
    public boolean checkIfUserExist(String username){
       return userRepo.existsByusername(username);


    }


}

