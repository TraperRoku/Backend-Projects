package com.TraperRoku.Recipe.Sharing.Platform.mapper;

import com.TraperRoku.Recipe.Sharing.Platform.dto.ChefDto;
import com.TraperRoku.Recipe.Sharing.Platform.dto.SignUpDto;
import com.TraperRoku.Recipe.Sharing.Platform.entity.Chef;
import org.mapstruct.Mapper;


public class ChefMapper {

    public Chef toChef(SignUpDto signUpDto){
        if ( signUpDto == null ) {
            return null;
        }

        Chef chef = new Chef();

        chef.setEmail(signUpDto.getEmail());
        chef.setPassword(signUpDto.getPassword());
        chef.setLogin(signUpDto.getLogin());
        chef.setLastName(signUpDto.getLastName());
        chef.setFirstName(signUpDto.getFirstName());


        return chef;
    }

    public  ChefDto toChefDto(Chef chef){
        if ( chef == null ) {
            return null;
        }

        ChefDto chefDto = new ChefDto();

        chefDto.setIdChef(chef.getIdChef());
        chefDto.setEmail(chef.getEmail());
        chefDto.setLogin(chef.getLogin());
        chefDto.setToken(chef.getFirstName());
        chefDto.setLastName(chef.getLastName());


        return chefDto;
    }
}

