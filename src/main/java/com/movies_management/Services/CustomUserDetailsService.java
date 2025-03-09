package com.movies_management.Services;



import com.movies_management.Config.UserRoles;
import com.movies_management.Entities.Users;
import com.movies_management.Repository.RolesRepo;
import com.movies_management.Repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    private final RolesRepo rolesRepo;
    public CustomUserDetailsService (UserRepo userRepo, RolesRepo rolesRepo){
        this.userRepo=userRepo;
        this.rolesRepo = rolesRepo;
    }


    @Override
    public UserRoles loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByusername(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserRoles(user,rolesRepo);
    }



}

