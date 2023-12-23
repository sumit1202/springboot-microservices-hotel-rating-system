package com.example.user_microservice.services;

import java.util.List;

import com.example.user_microservice.entities.User;

public interface UserService {
    User saveUser(User user);

    User getUser(String id);

    List<User> getAllUsers();
}
