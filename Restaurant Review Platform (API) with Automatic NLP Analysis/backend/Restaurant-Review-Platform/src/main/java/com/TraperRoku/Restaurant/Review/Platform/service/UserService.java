package com.TraperRoku.Restaurant.Review.Platform.service;

import com.TraperRoku.Restaurant.Review.Platform.dto.CredentialsDto;
import com.TraperRoku.Restaurant.Review.Platform.dto.SignUpDto;
import com.TraperRoku.Restaurant.Review.Platform.dto.UserDto;

import com.TraperRoku.Restaurant.Review.Platform.entity.User;
import com.TraperRoku.Restaurant.Review.Platform.exception.AppException;
import com.TraperRoku.Restaurant.Review.Platform.mappers.UserMapper;
import com.TraperRoku.Restaurant.Review.Platform.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service

public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapper();
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }

    public User findByLogin(String login){
    return userRepository.findByLogin(login).orElseThrow(()-> new IllegalArgumentException("Not found user"));
    }

    public UserDto login(CredentialsDto credentialsDto){
        User userOptional = userRepository.findByLogin(credentialsDto.getLogin()).orElseThrow(()->new IllegalArgumentException("Not found"));
        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()),userOptional.getPassword())){
            return userMapper.toUserDto(userOptional);
        }
        throw new AppException("Invalid Password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto signUpDto){
        Optional<User> optionalUser = userRepository.findByLogin(signUpDto.getLogin());

        if(optionalUser.isPresent()){
            throw new AppException("There is user with that login", HttpStatus.FOUND);
        }
        User user = userMapper.toUser(signUpDto);
        user.setPassword(passwordEncoder.encode(new String(signUpDto.getPassword())));
        return userMapper.toUserDto(user);


    }
}
