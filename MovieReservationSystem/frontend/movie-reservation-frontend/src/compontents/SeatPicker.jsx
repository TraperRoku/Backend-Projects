import React, { useEffect, useState } from 'react';
import { fetchSeats } from '../services/api';

const SeatPicker = ({ movieScheduleId, selectedSeats, onSeatSelect }) => {
  const [seats, setSeats] = useState([]);

  useEffect(() => {
    fetchSeats(movieScheduleId).then((response) => setSeats(response.data));
  }, [movieScheduleId]);

  const handleSeatClick = (seatId) => {
    if (selectedSeats.includes(seatId)) {
      onSeatSelect(selectedSeats.filter((id) => id !== seatId));
    } else {
      onSeatSelect([...selectedSeats, seatId]);
    }
  };

  return (
    <div className="seat-grid">
      {seats.map((seat) => (
        <button
          key={seat.id}
          onClick={() => handleSeatClick(seat.id)}
          className={`seat ${seat.status} ${selectedSeats.includes(seat.id) ? 'selected' : ''}`}
          disabled={seat.status !== 'AVAILABLE'}
        >
          {seat.seatNumber}
        </button>
      ))}
    </div>
  );
};

export default SeatPicker;