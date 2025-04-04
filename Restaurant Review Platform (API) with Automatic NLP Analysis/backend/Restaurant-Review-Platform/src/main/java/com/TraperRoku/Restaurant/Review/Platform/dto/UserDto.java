package com.TraperRoku.Restaurant.Review.Platform.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {


        private String id;

        private String login;
        private String email;
        private String token;

}
