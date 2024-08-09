package com.Filip.Expense.Tracker.API.service;

import com.Filip.Expense.Tracker.API.model.User;
import com.Filip.Expense.Tracker.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private  UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
