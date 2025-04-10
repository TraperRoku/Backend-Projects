package com.TraperRoku.Restaurant.Review.Platform.repository;

import com.TraperRoku.Restaurant.Review.Platform.entity.Restaurant;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository extends MongoRepository<Restaurant, ObjectId> {
}
