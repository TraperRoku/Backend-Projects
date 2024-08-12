package com.TraperRoku.shorteringAPI.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String shortUrl;
    @Lob
    private String originalUrl;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

}
