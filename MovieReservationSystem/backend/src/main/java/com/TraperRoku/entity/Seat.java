package com.TraperRoku.entity;

import com.TraperRoku.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "schedule_id",nullable = false)
    private MovieSchedule schedule;

    private int rowNumber;
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;




}
