package com.TraperRoku.service;

import com.TraperRoku.dto.CredentialsDto;
import com.TraperRoku.dto.SignUpDto;
import com.TraperRoku.dto.UserDto;
import com.TraperRoku.entity.User;
import com.TraperRoku.exception.AppException;
import com.TraperRoku.mapper.UserMapper;
import com.TraperRoku.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


   private final UserRepository userRepository;
   private final UserMapper userMapper= new UserMapper();
    private final PasswordEncoder passwordEncoder;
    public UserDto findByLogin(String login){
      User user = userRepository.findByLogin(login).orElseThrow(()-> new IllegalArgumentException("Unknown user"));
      return userMapper.toUserDto(user);
    }

    public UserDto register(SignUpDto signUpDto) {
        Optional<User> optionalUser = userRepository.findByLogin(signUpDto.getLogin());


        if (optionalUser.isPresent()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Login already exists");
        }

       User user = userMapper.toUser(signUpDto);
        user.setRole("USER");


        user.setPassword(passwordEncoder.encode(new String(signUpDto.getPassword())));
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    public UserDto login(CredentialsDto credentialsDto){
        User user = userRepository.findByLogin(credentialsDto.getLogin()).orElseThrow(()->
                new IllegalArgumentException("Unknown user"));
        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())){
            return userMapper.toUserDto(user);
        }
        throw new AppException(HttpStatus.BAD_REQUEST, "Invalid Password");
    }
}
