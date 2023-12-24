package com.example.rating_microservice.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.rating_microservice.entities.Rating;

public interface RatingRepository extends MongoRepository<Rating, String> {

    // custom method to find all ratings by user id
    List<Rating> findByUserId(String userId);

    // custom method to find all ratings by hotel id
    List<Rating> findByHotelId(String hotelId);

}
