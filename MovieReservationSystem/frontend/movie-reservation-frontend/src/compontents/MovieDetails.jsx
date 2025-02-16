import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { fetchMovieDetails, fetchMovieSchedules } from '../services/api';
import SeatPicker from './SeatPicker';
import ReservationForm from './ReservationForm';
import './Movie.css';

const MovieDetails = () => {
  const { id } = useParams();
  const [movie, setMovie] = useState(null);
  const [schedules, setSchedules] = useState([]);
  const [selectedScheduleId, setSelectedScheduleId] = useState(null);
  const [selectedSeats, setSelectedSeats] = useState([]);

  useEffect(() => {
    fetchMovieDetails(id).then((response) => setMovie(response.data));
  }, [id]);

  useEffect(() => {
    if (movie) {
      fetchMovieSchedules(movie.id).then((response) => setSchedules(response.data));
    }
  }, [movie]);

  if (!movie) return <div>Loading...</div>;

  return (
    <div>
      <h1>{movie.title}</h1>
      <p>{movie.description}</p>
      <img src={movie.posterUrl} alt={movie.title} />

      <h2>Wybierz datę i godzinę seansu:</h2>
      <select onChange={(e) => setSelectedScheduleId(e.target.value)}>
        <option value="">Wybierz seans</option>
        {schedules.map((schedule) => (
          <option key={schedule.id} value={schedule.id}>
            {new Date(schedule.startTime).toLocaleString()}
          </option>
        ))}
      </select>

      {selectedScheduleId && (
        <>
          <h2>Wybierz miejsca:</h2>
          <SeatPicker
            movieScheduleId={selectedScheduleId}
            selectedSeats={selectedSeats}
            onSeatSelect={setSelectedSeats}
          />
          <ReservationForm
            movieScheduleId={selectedScheduleId}
            selectedSeats={selectedSeats}
          />
        </>
      )}
    </div>
  );
};

export default MovieDetails;