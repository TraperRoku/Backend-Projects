package com.TraperRoku.backend.service;

import com.TraperRoku.backend.Dto.CredentialsDto;
import com.TraperRoku.backend.Dto.SignUpDto;
import com.TraperRoku.backend.Dto.UserDto;
import com.TraperRoku.backend.entities.User;
import com.TraperRoku.backend.exceptions.AppException;
import com.TraperRoku.backend.mappers.UserMapper;
import com.TraperRoku.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public UserDto findByLogin(String login){
        User user = userRepository.findByLogin(login).orElseThrow(
                () -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserDto login (CredentialsDto credentialsDto){
        User user = userRepository.findByLogin(credentialsDto.getLogin()).
                orElseThrow(() -> new AppException("Unknown user",HttpStatus.NOT_FOUND));
        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())){
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public User findByLogin1(String login){
        User user = userRepository.findByLogin(login).orElseThrow(()->new RuntimeException());
        return user;
    }


    public UserDto register(SignUpDto signUpDto){
        Optional<User> optionalUser = userRepository.findByLogin(signUpDto.getLogin());

        if(optionalUser.isPresent()){
            throw new AppException("Login already exists",HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(signUpDto);

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.getPassword())));

        userRepository.save( user);
        return userMapper.toUserDto(user);
    }


}
