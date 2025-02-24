import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './Movie.css';

const BASE_URL = "http://localhost:8080";

const MovieList = () => {
  const [movies, setMovies] = useState([]);
  const [selectedDate, setSelectedDate] = useState(new Date().toISOString().split("T")[0]);
  const [currentWeekStart, setCurrentWeekStart] = useState(new Date());

  const goToPreviousWeek = () => {
    const newWeekStart = new Date(currentWeekStart);
    newWeekStart.setDate(newWeekStart.getDate() - 7);
    setCurrentWeekStart(newWeekStart);
  };

  const goToNextWeek = () => {
    const newWeekStart = new Date(currentWeekStart);
    newWeekStart.setDate(newWeekStart.getDate() + 7);
    setCurrentWeekStart(newWeekStart);
  };

  useEffect(() => {
    const fetchMoviesByDate = async () => {
      try {
        const response = await axios.get(`${BASE_URL}/api/movies/schedule?date=${selectedDate}`);
        console.log("Response data:", response.data); 
        setMovies(response.data);
      } catch (err) {
        console.error("Błąd podczas pobierania repertuaru", err);
      }
    };
    fetchMoviesByDate();
  }, [selectedDate]);

  const generateWeekDays = () => {
    const days = [];
    const startOfWeek = new Date(currentWeekStart);
    startOfWeek.setDate(startOfWeek.getDate() - startOfWeek.getDay());
    for (let i = 0; i < 7; i++) {
      const date = new Date(startOfWeek);
      date.setDate(date.getDate() + i);
      days.push(date);
    }
    return days;
  };

  const formatShowTime = (showTime) => {
    if (!showTime) return "Brak godziny";
    if (Array.isArray(showTime) && showTime.length === 5) {
      const [, , , hour, minute] = showTime;
      return `${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}`;
    }
    return showTime; // Fallback
  };

  // Helper function to check if a showtime matches the selected date
  const isShowtimeOnSelectedDate = (showTime) => {
    if (!showTime || !Array.isArray(showTime) || showTime.length !== 5) return false;
    const [year, month, day] = showTime;
    const showtimeDate = `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    return showtimeDate === selectedDate;
  };

  return (
    <div className="movie-list-container">
      <h1>📅 Wybierz dzień</h1>

      <div className="week-navigation">
        <button onClick={goToPreviousWeek}>Poprzedni tydzień</button>
        <button onClick={goToNextWeek}>Następny tydzień</button>
      </div>

      <div className="date-picker">
        {generateWeekDays().map((date, index) => {
          const formattedDate = date.toISOString().split("T")[0];
          const dayNames = ["Niedziela", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota"];
          const dayName = dayNames[date.getDay()];

          return (
            <button
              key={formattedDate}
              onClick={() => setSelectedDate(formattedDate)}
              className={selectedDate === formattedDate ? "active" : ""}
            >
              {dayName} ({formattedDate})
            </button>
          );
        })}
      </div>

      <div className="movie-list">
        {movies.length === 0 ? (
          <p>Brak filmów na wybrany dzień</p>
        ) : (
          movies.map((movie) => (
            <div key={movie.id} className="movie-card">
              <img src={movie.posterUrl} alt={movie.title} />
              <h2>
              <Link
  to={`/movie/${movie.id}`}
  state={{ selectedDate }} // Pass the selected date
>
  {movie.title}
</Link>
              </h2>
              <p>{movie.description}</p>
              <h3>🎥 Seanse:</h3>
              {movie.schedules && movie.schedules.length > 0 ? (
                movie.schedules
                  .filter((schedule) => isShowtimeOnSelectedDate(schedule.showTime)) // Filter showtimes by selected date
                  .map((schedule) => (
                    <div key={schedule.id}>
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
          ))
        )}
      </div>
    </div>
  );
};

export default MovieList;