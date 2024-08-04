package com.To_Do.List.API.To_Do.List.API.service;

import com.To_Do.List.API.To_Do.List.API.model.User;
import com.To_Do.List.API.To_Do.List.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
