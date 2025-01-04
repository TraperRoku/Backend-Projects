package com.TraperRoku.Recipe.Sharing.Platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<RecipeImage> images;

    @ManyToOne
    @JoinColumn(name = "chef_id", nullable = false)
    private Chef chef;


}
