package com.movies_management.Repository;


import com.movies_management.Entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RolesRepo extends JpaRepository<Roles,Integer> {

    @Query("SELECT r.role FROM Roles r WHERE r.id = :roleId")
    String findRoleNameById(@Param("roleId") Integer roleId);


}

