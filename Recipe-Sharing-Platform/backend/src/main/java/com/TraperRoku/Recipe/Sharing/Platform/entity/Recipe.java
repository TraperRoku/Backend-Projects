package com.TraperRoku.Recipe.Sharing.Platform.entity;

import com.TraperRoku.Recipe.Sharing.Platform.Enum.DifficultyRecipe;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @Enumerated(EnumType.STRING)
    @JsonProperty("difficulty")
    private DifficultyRecipe difficulty;

    private double time;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<RecipeImage> images = new ArrayList<>();  // Initialize the list

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chef_id", nullable = false)
    private Chef chef;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", fetch = FetchType.EAGER)
    @OrderBy("stepNumber ASC")
    @JsonManagedReference
    private List<RecipeStep> steps = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    private LocalDate publicationDate;

    @ElementCollection
    @CollectionTable(name = "recipe_tags" , joinColumns = @JoinColumn(name = "reicpe_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();


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