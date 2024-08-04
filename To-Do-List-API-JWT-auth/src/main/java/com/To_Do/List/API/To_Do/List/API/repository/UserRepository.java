package com.To_Do.List.API.To_Do.List.API.repository;

import com.To_Do.List.API.To_Do.List.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
     Optional<User> findByUsername(String username);
     Boolean existsByUsername(String username);

     @Modifying
     @Query(value = "DELETE FROM user_roles ur WHERE ur.user_id = :userId", nativeQuery = true)
     void deleteRolesByUserId(@Param("userId") int userId);
}
