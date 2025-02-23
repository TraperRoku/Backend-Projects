package com.TraperRoku.entity;

import com.TraperRoku.enums.SeatCategory;
import com.TraperRoku.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "movie_schedule_id",nullable = false)
    private MovieSchedule movieSchedule;

    @Column(name = "row_num")
    private int rowNumber;

    @Column(name = "seat_num")
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)

    private SeatCategory category;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    public double getPrice() {
        if (category == null) {
            return 20.00;
        }

        switch (category) {
            case VIP:
                return 50.00;
            case ECONOMY:
                return 15.00;
            default:
                return 20.00;
        }
    }
}
