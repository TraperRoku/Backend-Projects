package com.TraperRoku.repository;

import com.TraperRoku.entity.Seat;
import com.TraperRoku.enums.ReservationStatus;

import com.TraperRoku.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Boolean existsByMovieScheduleIdAndIdAndStatus(Long movieScheduleId, Long id, SeatStatus status);

    List<Seat> findAllByMovieScheduleIdAndIdInAndStatus(Long movieScheduleId, List<Long> ids, SeatStatus status);

    List<Seat> findByMovieScheduleId(Long movieScheduleId);

}
