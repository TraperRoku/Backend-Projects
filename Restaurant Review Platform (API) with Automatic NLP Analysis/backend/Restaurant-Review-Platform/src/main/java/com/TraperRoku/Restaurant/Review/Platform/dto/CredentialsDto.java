package com.TraperRoku.Restaurant.Review.Platform.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class CredentialsDto {

    private String login;
    private String password;
}