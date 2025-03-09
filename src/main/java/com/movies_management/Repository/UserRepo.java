package com.movies_management.Repository;



import com.movies_management.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<Users,Integer> {

    Users findByusername(String username);

    Users findByemail(String email);



}
