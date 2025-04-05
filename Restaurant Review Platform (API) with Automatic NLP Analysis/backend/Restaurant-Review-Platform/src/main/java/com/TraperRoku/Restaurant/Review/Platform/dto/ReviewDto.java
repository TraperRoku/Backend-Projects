package com.TraperRoku.Restaurant.Review.Platform.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {
    private String text;
    private int rating;
    private int sentimentScore;
    private LocalDateTime createdAt;
}
