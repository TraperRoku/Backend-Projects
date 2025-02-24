import React, { useEffect, useState } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import axiosInstance from '../axios_helper';
import { Link } from 'react-router-dom';
import './Movie.css';

const MovieDetails = () => {
  const { id } = useParams(); 
  const location = useLocation(); 
  const [movie, setMovie] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedDate, setSelectedDate] = useState(null);

  useEffect(() => {
    if (location.state?.selectedDate) {
      setSelectedDate(location.state.selectedDate);
    }

    const fetchMovieDetails = async () => {
      try {
        setLoading(true);
        const response = await axiosInstance.get(`/api/movies/${id}`);
        setMovie(response.data);
      } catch (err) {
        console.error("Error fetching movie details:", err);
        if (err.response?.status === 401) {
          setError('Sesja wygasła. Zaloguj się ponownie.');
        } else {
          setError('Nie udało się pobrać szczegółów filmu');
        }
      } finally {
        setLoading(false);
      }
    };

    if (id) {
      fetchMovieDetails();
    }
  }, [id, location.state]);

  const isShowtimeOnSelectedDate = (showTime) => {
    if (!showTime || !Array.isArray(showTime) || showTime.length !== 5) return false;
    const showtimeDate = `${showTime[0]}-${String(showTime[1]).padStart(2, '0')}-${String(showTime[2]).padStart(2, '0')}`;
    return showtimeDate === selectedDate;
  };

  if (loading) return <div>Ładowanie...</div>;
  if (error) return <div className="error-message">{error}</div>;
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

        <div className="showtime-selection">
          <h3>🎥 Seanse na dzień {selectedDate}:</h3>
          {movie.schedules && movie.schedules.length > 0 ? (
            movie.schedules
              .filter((schedule) => isShowtimeOnSelectedDate(schedule.showTime)) // Filter showtimes by selected date
              .map((schedule) => (
                <div key={schedule.id} className="showtime-option">
                  <p>
                    ⏰ {formatShowTime(schedule.showTime)} -  
                    <Link to={`/reservation/${schedule.id}`} className="reserve-btn">
                      Zarezerwuj
                    </Link>
                  </p>
                </div>
              ))
          ) : (
            <p>❌ Brak dostępnych seansów</p>
          )}
        </div>
      </div>
    </div>
  );
};

const formatShowTime = (showTime) => {
  if (!showTime || !Array.isArray(showTime) || showTime.length !== 5) return "Brak godziny";
  const [, , , hour, minute] = showTime; 
  return `${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}`;
};

export default MovieDetails;