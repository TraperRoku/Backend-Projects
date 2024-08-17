package com.TraperRoku.FitnessTracker.repository;

import com.TraperRoku.FitnessTracker.model.ERole;
import com.TraperRoku.FitnessTracker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
