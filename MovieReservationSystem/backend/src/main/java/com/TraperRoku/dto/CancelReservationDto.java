package com.TraperRoku.dto;

import com.TraperRoku.entity.Seat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CancelReservationDto {
    Long reservationId;
    List<Long> SeatIds;
}
