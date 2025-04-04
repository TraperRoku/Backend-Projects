package com.TraperRoku.Restaurant.Review.Platform.entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "restaurants")
public class Restaurant {
    @Id
    private ObjectId RestaurantId;
    private String name;
    private String address;


}
