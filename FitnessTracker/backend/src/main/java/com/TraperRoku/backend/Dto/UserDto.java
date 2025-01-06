package com.TraperRoku.backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

    private Long id;
    private String firstname;
    private String lastName;
    private String login;
    private String token;

}
