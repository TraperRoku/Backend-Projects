package com.TraperRoku.Restaurant.Review.Platform.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
@Builder
public class User {
    @Id
    private ObjectId UserId;
    private String login;
    private String email;
    private String password;
}
