package com.example.hotel_microservice.services;

import java.util.List;

import com.example.hotel_microservice.entities.Hotel;

public interface HotelService {
    Hotel createHotel(Hotel hotel);

    Hotel getHotel(String id);

    List<Hotel> getAllHotels();
}
