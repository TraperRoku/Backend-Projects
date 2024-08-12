package com.TraperRoku.shorteringAPI.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UrlErrorDto {
    private String error;
    private String status;
}
