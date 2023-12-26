package com.example.user_microservice.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_microservice.entities.User;
import com.example.user_microservice.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
    int retryCount = 1;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    @CircuitBreaker(name = "ratingHotelBreaker2", fallbackMethod = "ratingHotelFallback2")
    @Retry(name = "ratingHotelRetry2", fallbackMethod = "ratingHotelFallback2")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("\n=> retryCount: " + (retryCount++));
        List<User> users = userService.getAllUsers();
        // return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelRetry", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        // return new ResponseEntity<User>(user, HttpStatus.OK);
        return ResponseEntity.ok(user);
    }

    // fallback method for ratingHotelBreaker circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception exception) {
        logger.info("\n=> ratingHotelFallback called with exception as: " + exception.getMessage());
        User user = User.builder()
                .firstName("NA")
                .lastName("NA")
                .email("NA")
                .id("NA")
                .build();
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // fallback method for ratingHotelBreaker2 circuit breaker
    public ResponseEntity<List<User>> ratingHotelFallback2(Exception exception) {
        logger.info("\\n" + //
                "\n=> ratingHotelFallback2 called with exception as: " + exception.getMessage());
        List<User> users = List.of(
                User.builder()
                        .firstName("NA")
                        .lastName("NA")
                        .email("NA")
                        .id("NA")
                        .build());
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

}
