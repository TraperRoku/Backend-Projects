package com.TraperRoku.repository;

import com.TraperRoku.dto.UserDto;
import com.TraperRoku.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<UserDto> findByLogin(String login);
}
