package com.TraperRoku.backend.mappers;

import com.TraperRoku.backend.Dto.SignUpDto;
import com.TraperRoku.backend.Dto.UserDto;
import com.TraperRoku.backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}