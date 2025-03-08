package com.movies_management.Repository;



import com.movies_management.Entities.resetpassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordRepo extends JpaRepository<resetpassword,Integer> {
    resetpassword findByemail(String email);
}

