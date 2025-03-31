package com.TraperRoku.Restaurant.Review.Platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {


        private Long id;

        private String login;
        private String email;
        private String token;

}
