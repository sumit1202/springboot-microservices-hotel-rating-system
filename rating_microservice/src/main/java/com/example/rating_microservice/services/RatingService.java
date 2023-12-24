package com.example.rating_microservice.services;

import java.util.List;

import com.example.rating_microservice.entities.Rating;

public interface RatingService {
    Rating createRating(Rating rating);

    List<Rating> getAllRatings();

    List<Rating> getAllRatingsByUserId(String userId);

    List<Rating> getAllRatingsByHotelId(String hotelId);
}
