package com.TraperRoku.Restaurant.Review.Platform.entity;


import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reviews")
@Getter
@Setter
public class Review {
    @Id
    private ObjectId ReviewId;
    private ObjectId userId;
    private ObjectId restaurantId;
    private String text;
    private int rating;
    private int sentimentScore;
    private LocalDateTime createdAt;
}
