package com.TraperRoku.Restaurant.Review.Platform.service;

import com.TraperRoku.Restaurant.Review.Platform.entity.Restaurant;
import com.TraperRoku.Restaurant.Review.Platform.exception.AppException;
import com.TraperRoku.Restaurant.Review.Platform.repository.RestaurantRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;

import java.util.List;

public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant createRestaurant(Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }
    public void updateRestaurant(ObjectId id){
        restaurantRepository.findById(id).orElseThrow(() -> new AppException("Not found restaurant with ID" + id, HttpStatus.NOT_FOUND));
    }

    public void deleteRestaurant(ObjectId id){
        restaurantRepository.deleteById(id);
    }
    public List<Restaurant> findAllRestaurant(){
        return restaurantRepository.findAll();
    }
}
