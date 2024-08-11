package com.TraperRoku.Note_taking.App.repository;

import com.TraperRoku.Note_taking.App.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note,Long> {
}
