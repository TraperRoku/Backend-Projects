package com.TraperRoku.Recipe.Sharing.Platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChef;

    @Column(nullable = false)
    @Size(max = 30)
    private String login;


    @Column(name = "first_name", nullable = false)
    @Size(max = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(max = 100)
    private String lastName;


    private String email;

    @Column(nullable = false)
    @Size(max = 100)

    public String password;

    @OneToMany(mappedBy = "chef", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Recipe> recipes;
}