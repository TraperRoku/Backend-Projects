package com.TraperRoku.repository;

import com.TraperRoku.entity.Movie;
import com.TraperRoku.entity.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieScheduleRepository extends JpaRepository<MovieSchedule,Long> {
}
