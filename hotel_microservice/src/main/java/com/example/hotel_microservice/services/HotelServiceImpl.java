package com.example.hotel_microservice.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotel_microservice.entities.Hotel;
import com.example.hotel_microservice.exceptions.ResourceNotFoundException;
import com.example.hotel_microservice.repositories.HotelRepository;

@Service
public class HotelServiceImpl implements HotelService {

    private HotelRepository hotelRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * Creates a new hotel.
     * Generates a random ID, sets it on the hotel object,
     * and saves the hotel to the repository.
     * 
     * @param hotel The Hotel object to create
     * @return The saved Hotel object with the generated ID
     */
    @Override
    public Hotel createHotel(Hotel hotel) {
        String id = UUID.randomUUID().toString();
        hotel.setId(id);
        return hotelRepository.save(hotel);
    }

    /**
     * Gets a hotel by id.
     * 
     * @param id The id of the hotel to retrieve
     * @return The Hotel object if found
     * @throws ResourceNotFoundException If no hotel with the given id exists
     */
    @Override
    public Hotel getHotel(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel id: " + id));
    }

    /**
     * Gets all hotels.
     * 
     * @return A list of all Hotel objects
     */
    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

}
