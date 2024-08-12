package com.TraperRoku.shorteringAPI.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UrlResponseDto {
    private String originalUrl;
    private LocalDateTime expirationDate;
    private String shortUrl;
}
