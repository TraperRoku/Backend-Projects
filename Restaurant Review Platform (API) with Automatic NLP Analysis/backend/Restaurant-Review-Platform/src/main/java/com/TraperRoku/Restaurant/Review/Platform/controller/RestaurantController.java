package com.TraperRoku.Restaurant.Review.Platform.controller;


import com.TraperRoku.Restaurant.Review.Platform.entity.Restaurant;
import com.TraperRoku.Restaurant.Review.Platform.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("restaurant")

public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @PostMapping
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant){
       return ResponseEntity.ok(restaurantRepository.save(restaurant));
    }
}
