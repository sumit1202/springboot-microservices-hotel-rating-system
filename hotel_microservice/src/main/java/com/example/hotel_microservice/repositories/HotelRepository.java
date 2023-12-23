package com.example.hotel_microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotel_microservice.entities.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {

}
