import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { createReservation } from '../services/api';
import { AuthContext } from '../contexts/AuthContext';

const ReservationForm = ({ movieScheduleId, selectedSeats }) => {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await createReservation({
        userId: user.id,
        movieScheduleId,
        seatIds: selectedSeats,
      });
      navigate(`/payment/${response.data.id}`);
    } catch (error) {
      alert('Błąd podczas rezerwacji!');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <button type="submit">Zarezerwuj</button>
    </form>
  );
};

export default ReservationForm;