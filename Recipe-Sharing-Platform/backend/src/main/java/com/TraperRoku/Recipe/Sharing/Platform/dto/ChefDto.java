package com.TraperRoku.Recipe.Sharing.Platform.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
public class ChefDto {

    private Long idChef;
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String token;

}