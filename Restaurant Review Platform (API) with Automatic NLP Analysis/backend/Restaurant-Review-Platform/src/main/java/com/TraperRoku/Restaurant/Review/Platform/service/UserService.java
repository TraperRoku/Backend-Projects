package com.TraperRoku.Restaurant.Review.Platform.service;

import com.TraperRoku.Restaurant.Review.Platform.dto.UserDto;
import com.TraperRoku.Restaurant.Review.Platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto findByLogin(String login){
    return userRepository.findByLogin(login).orElseThrow(()-> new IllegalArgumentException("Not found user"));
    }
}
