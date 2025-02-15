import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080/api',
});

export const fetchMovies = () => API.get('/movies');
export const fetchMovieDetails = (movieId) => API.get(`/movies/${movieId}`);
export const fetchMovieSchedules = (movieId) => API.get(`/movies/${movieId}/schedules`);
export const fetchSeats = (movieScheduleId) => API.get(`/schedules/${movieScheduleId}/seats`);
export const createReservation = (reservationData) => API.post('/reservations', reservationData);