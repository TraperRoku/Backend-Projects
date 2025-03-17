package com.TraperRoku.Restaurant.Review.Platform.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private String userId;
    private String restaurantId;
    private String text;
    private int sentimentScore;
}
