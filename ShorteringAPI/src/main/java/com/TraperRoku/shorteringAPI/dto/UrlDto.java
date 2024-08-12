package com.TraperRoku.shorteringAPI.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UrlDto {
    private String url;
    private String expirationDate;
}
