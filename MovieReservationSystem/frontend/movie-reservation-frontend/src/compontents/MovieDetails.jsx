import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './Movie.css';

const MovieDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [movie, setMovie] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchMovieDetails = async () => {
      try {
        setLoading(true);
        const response = await axios.get(`http://localhost:8080/api/movies/${id}`);
        setMovie(response.data);
      } catch (err) {
        setError('Nie udało się pobrać szczegółów filmu');
      } finally {
        setLoading(false);
      }
    };

    if (id) {
      fetchMovieDetails();
    }
  }, [id]);

  // Obsługa rezerwacji
  const handleReservation = () => {
    navigate(`/reservation/${id}`); // Przekierowanie do strony rezerwacji
  };

  if (loading) return <div>Ładowanie...</div>;
  if (error) return <div>Błąd: {error}</div>;
  if (!movie) return <div>Nie znaleziono filmu</div>;

  return (
    <div className="movie-details">
      <div className="movie-header">
        <h1 className="movie-title">{movie.title}</h1>
        {movie.posterUrl && (
          <img 
            src={movie.posterUrl} 
            alt={movie.title} 
            className="movie-poster"
          />
        )}
      </div>
      
      <div className="movie-info">
        <div className="movie-description">
          <h2>Opis</h2>
          <p>{movie.description}</p>
        </div>

        <div className="movie-metadata">
          {movie.duration && (
            <div className="movie-duration">
              <strong>Czas trwania:</strong> {movie.duration} min
            </div>
          )}
          
          {movie.genre && movie.genre.length > 0 && (
            <div className="movie-genres">
              <strong>Gatunki:</strong> {movie.genre.join(', ')}
            </div>
          )}
        </div>

        {/* 🔥 Nowy przycisk rezerwacji */}
        <button onClick={handleReservation} className="reserve-button">
          🎟 Zarezerwuj miejsce
        </button>

      </div>
    </div>
  );
};

export default MovieDetails;
