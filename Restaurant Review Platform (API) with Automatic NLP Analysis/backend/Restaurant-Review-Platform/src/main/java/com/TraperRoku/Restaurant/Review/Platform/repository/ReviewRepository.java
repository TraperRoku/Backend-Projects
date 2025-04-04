package com.TraperRoku.Restaurant.Review.Platform.repository;

import com.TraperRoku.Restaurant.Review.Platform.entity.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, ObjectId> {
    List<Review> findByRestaurantId(String restaurantId);

}
