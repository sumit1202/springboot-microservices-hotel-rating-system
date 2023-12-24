package com.example.user_microservice.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.user_microservice.entities.Hotel;
import com.example.user_microservice.entities.Rating;
import com.example.user_microservice.entities.User;
import com.example.user_microservice.exceptions.ResourceNotFoundException;
import com.example.user_microservice.repositories.UserRepository;
import com.example.user_microservice.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public User saveUser(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> thUsers = userRepository.findAll();

        // fetch and set each user's ratings
        for (User user : thUsers) {
            fetchAndSetTheUserRatingsWithHotel(user.getId(), user);
        }

        return thUsers;
    }

    @Override
    public User getUser(String id) {

        // get single user by id
        User theUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + ", not found."));

        // fetch and set theUser's ratings
        fetchAndSetTheUserRatingsWithHotel(id, theUser);

        return theUser;
    }

    private void fetchAndSetTheUserRatingsWithHotel(String id, User theUser) {
        // Call ratings microservice to get ratings for user
        Rating[] theUserRatingsArr = restTemplate.getForObject("http://localhost:8083/ratings/users/" + id,
                Rating[].class);

        // Convert ratings array to list
        List<Rating> theUserRatingsList = List.of(theUserRatingsArr);

        // Log ratings
        logger.info("\n=> theUserRatings: " + theUserRatingsArr);

        // Enrich each rating with hotel data
        for (Rating rating : theUserRatingsArr) {

            // Call hotel microservice to get hotel for rating
            Hotel hotel = restTemplate.getForObject("http://localhost:8082/hotels/" + rating.getHotelId(), Hotel.class);

            // Set hotel on rating
            rating.setHotel(hotel);
        }

        // Set ratings on user
        theUser.setRatings(theUserRatingsList);
    }

}
