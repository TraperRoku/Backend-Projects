package com.TraperRoku.Recipe.Sharing.Platform.repository;

import com.TraperRoku.Recipe.Sharing.Platform.entity.Chef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChefRepository extends JpaRepository<Chef,Long> {
    Optional<Chef> findByLogin(String login);
}
