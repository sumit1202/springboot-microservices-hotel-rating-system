package com.example.user_microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.user_microservice.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    //? spring data jpa will automatically provide free CRUD methods for us.
    //? custom CRUD methods can be added as well.
}
