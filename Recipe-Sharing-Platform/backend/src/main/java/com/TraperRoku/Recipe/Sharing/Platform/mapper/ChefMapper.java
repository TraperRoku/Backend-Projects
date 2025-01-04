package com.TraperRoku.Recipe.Sharing.Platform.mapper;

import com.TraperRoku.Recipe.Sharing.Platform.dto.ChefDto;
import com.TraperRoku.Recipe.Sharing.Platform.dto.SignUpDto;
import com.TraperRoku.Recipe.Sharing.Platform.entity.Chef;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChefMapper {

    Chef toChef(SignUpDto signUpDto);

    ChefDto toChefDto(Chef chef);
}

