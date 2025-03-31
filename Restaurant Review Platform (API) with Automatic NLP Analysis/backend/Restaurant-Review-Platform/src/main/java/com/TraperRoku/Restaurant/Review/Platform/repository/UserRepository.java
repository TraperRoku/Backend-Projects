package com.TraperRoku.Restaurant.Review.Platform.repository;

import com.TraperRoku.Restaurant.Review.Platform.dto.UserDto;
import com.TraperRoku.Restaurant.Review.Platform.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByLogin(String login);
}
