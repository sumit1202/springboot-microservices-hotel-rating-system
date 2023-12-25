package com.example.user_microservice.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.user_microservice.entities.Hotel;

@FeignClient("HOTEL-MICROSERVICE")
public interface HotelService {

    @GetMapping("/hotels/{hotelId}")
    Hotel getHotelById(@PathVariable String hotelId);
    
}
