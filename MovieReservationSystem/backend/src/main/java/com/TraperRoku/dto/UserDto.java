package com.TraperRoku.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String token;

    private String role;
}
