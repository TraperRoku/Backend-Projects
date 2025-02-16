package com.TraperRoku.mapper;

import com.TraperRoku.dto.SignUpDto;
import com.TraperRoku.dto.UserDto;
import com.TraperRoku.entity.User;

public class UserMapper {
    public User toUser(SignUpDto signUpDto){
        if ( signUpDto == null ) {
            return null;
        }
        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setPassword(signUpDto.getPassword());
        user.setLogin(signUpDto.getLogin());
        user.setLastName(signUpDto.getLastName());
        user.setFirstName(signUpDto.getFirstName());


        return user;
    }

    public UserDto toUserDto(User user){
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setToken(user.getFirstName());
        userDto.setEmail(user.getEmail());
        userDto.setLastName(user.getLastName());
        userDto.setLogin(user.getLogin());
        userDto.setFirstName(user.getFirstName());
        userDto.setRole(user.getRole());


        return userDto;
    }


}
