package com.TraperRoku.Restaurant.Review.Platform.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@AllArgsConstructor
@Builder
@Data
public class ErrorDto {
    private String message;

}