package com.TraperRoku.Restaurant.Review.Platform.mappers;


import com.TraperRoku.Restaurant.Review.Platform.dto.SignUpDto;

import com.TraperRoku.Restaurant.Review.Platform.dto.UserDto;
import com.TraperRoku.Restaurant.Review.Platform.entity.User;



public class UserMapper {
    public User toUser(SignUpDto signUpDto) {
        if (signUpDto == null) {
            return null;
        }

        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setLogin(signUpDto.getLogin());
        user.setPassword(signUpDto.getPassword());
        return user;
    }

    public UserDto toUserDto(User user){
        if(user == null){
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setLogin(user.getLogin());
        userDto.setToken(user.getLogin()); // LINIJKA Z TOKEN

        return  userDto;
    }
}
