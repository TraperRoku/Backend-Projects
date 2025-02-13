package com.TraperRoku.service;

import com.TraperRoku.dto.UserDto;
import com.TraperRoku.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {


   private final UserRepository userRepository;
    public UserDto findByLogin(String login){
       return userRepository.findByLogin(login).orElseThrow(()->
               new RuntimeException("There is not login like this"));
    }
}
