package com.TraperRoku.Restaurant.Review.Platform.mappers;


import com.TraperRoku.Restaurant.Review.Platform.dto.SignUpDto;

import com.TraperRoku.Restaurant.Review.Platform.dto.UserDto;
import com.TraperRoku.Restaurant.Review.Platform.entity.User;



public class UserMapper {
    public User toUser(SignUpDto signUpDto) {
        if (signUpDto == null) {
            return null;
        }
        return User.builder()
                .email(signUpDto.getEmail())
                .login(signUpDto.getLogin())
                .password(signUpDto.getPassword())
                .build();
    }

    public UserDto toUserDto(User user){
        if(user == null){
            return null;
        }

        return UserDto.builder().id(user.getUserId().toString())
                .email(user.getEmail())
                .login(user.getLogin())
                .token(user.getLogin())
                .build();
    }
}
