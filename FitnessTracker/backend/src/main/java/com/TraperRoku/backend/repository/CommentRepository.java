package com.TraperRoku.backend.repository;

import com.TraperRoku.backend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
