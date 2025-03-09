package com.movies_management.Repository;



import com.movies_management.Entities.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordRepo extends JpaRepository<ResetPassword,Integer> {
    ResetPassword findByemail(String email);
}

