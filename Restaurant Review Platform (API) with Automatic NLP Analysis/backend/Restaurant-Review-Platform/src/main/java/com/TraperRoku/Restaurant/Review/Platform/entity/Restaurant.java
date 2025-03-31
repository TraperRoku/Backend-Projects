package com.TraperRoku.Restaurant.Review.Platform.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "restaurants")
public class Restaurant {
    @Id
    private String id;
    private String name;
    private String address;


}
