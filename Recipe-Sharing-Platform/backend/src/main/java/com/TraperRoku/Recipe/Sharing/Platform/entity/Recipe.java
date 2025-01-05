package com.TraperRoku.Recipe.Sharing.Platform.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<RecipeImage> images = new ArrayList<>();  // Initialize the list

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chef_id", nullable = false)
    private Chef chef;

    // Remove @Data annotation and explicitly implement toString to prevent infinite recursion
    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imagesCount=" + (images != null ? images.size() : 0) +
                ", chef=" + (chef != null ? chef.getLogin() : "null") +
                '}';
    }
}