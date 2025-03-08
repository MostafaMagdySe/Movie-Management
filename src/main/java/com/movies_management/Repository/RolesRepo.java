package com.movies_management.Repository;


import com.movies_management.Entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Roles,Integer> {
}

