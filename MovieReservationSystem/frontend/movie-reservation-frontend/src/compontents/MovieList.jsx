import React, { useEffect, useState, useCallback } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './Movie.css';

const BASE_URL = "http://localhost:8080";

const MovieList = () => {
  const [movies, setMovies] = useState([]);
  const [filteredMovies, setFilteredMovies] = useState([]);
  const [selectedDate, setSelectedDate] = useState(new Date().toISOString().split("T")[0]);
  const [currentWeekStart, setCurrentWeekStart] = useState(new Date());
  const [filters, setFilters] = useState({
    title: "",
    genre: ""
  });

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


  const filterMovies = useCallback(() => {
    const filtered = movies.filter((movie) => {
      const titleMatch = movie.title.toLowerCase().includes(filters.title.toLowerCase());
      const genreMatch = filters.genre === "" || 
        (movie.genre && movie.genre.some(g => 
          g.toLowerCase().includes(filters.genre.toLowerCase())
        ));
      
      return titleMatch && genreMatch;
    });
    
    setFilteredMovies(filtered);
  }, [filters, movies]);


  useEffect(() => {
    const fetchMoviesByDate = async () => {
      try {
        const response = await axios.get(`${BASE_URL}/api/movies/schedule?date=${selectedDate}`);
        console.log("Response data:", response.data); 
        setMovies(response.data);
        setFilteredMovies(response.data); 
      } catch (err) {
        console.error("Błąd podczas pobierania repertuaru", err);
      }
    };
    fetchMoviesByDate();
  }, [selectedDate]);

  
  useEffect(() => {
    filterMovies();
  }, [filterMovies]); 


  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilters((prevFilters) => ({
      ...prevFilters,
      [name]: value
    }));
  };

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
    return showTime; 
  };


  const isShowtimeOnSelectedDate = (showTime) => {
    if (!showTime || !Array.isArray(showTime) || showTime.length !== 5) return false;
    const [year, month, day] = showTime;
    const showtimeDate = `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    return showtimeDate === selectedDate;
  };

  return (
    <div className="movie-list-container">
      <h1>📅Choose day</h1>

      <div className="week-navigation">
        <button onClick={goToPreviousWeek}>Last week</button>
        <button onClick={goToNextWeek}>Next week</button>
      </div>

      <div className="date-picker">
        {generateWeekDays().map((date, index) => {
          const formattedDate = date.toISOString().split("T")[0];
          const dayNames = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"];
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

   
      <div className="search-filters">
        <h2>🔍 Find movie</h2>
        <div className="filter-inputs">
          <div className="filter-group">
            <label htmlFor="title">Title:</label>
            <input
              type="text"
              id="title"
              name="title"
              value={filters.title}
              onChange={handleFilterChange}
              placeholder="Write title..."
            />
          </div>
          
          <div className="filter-group">
            <label htmlFor="genre">Genre:</label>
            <input
              type="text"
              id="genre"
              name="genre"
              value={filters.genre}
              onChange={handleFilterChange}
              placeholder="Write genre..."
            />
          </div>
        </div>
      </div>

      <div className="movie-list">
        {filteredMovies.length === 0 ? (
          <p>Not found any Movie</p>
        ) : (
          filteredMovies.map((movie) => (
            <div key={movie.id} className="movie-card">
              <img src={movie.posterUrl} alt={movie.title} />
              <h2>
                <Link
                  to={`/movie/${movie.id}`}
                  state={{ selectedDate }}
                >
                  {movie.title}
                </Link>
              </h2>
              {movie.genre && movie.genre.length > 0 && (
                <div className="movie-genres">
                  <strong>Genre:</strong> {movie.genre.join(", ")}
                </div>
              )}
              <p>{movie.description}</p>
              <h3>🎥 ShowTime:</h3>
              {movie.schedules && movie.schedules.length > 0 ? (
                movie.schedules
                  .filter((schedule) => isShowtimeOnSelectedDate(schedule.showTime))
                  .map((schedule) => (
                    <div key={schedule.id}>
                      <p>
                        ⏰ {formatShowTime(schedule.showTime)} -  
                        <Link to={`/reservation/${schedule.id}`} className="reserve-btn">
                          Reserve
                        </Link>
                      </p>
                    </div>
                  ))
              ) : (
                <p>❌ Not found showtimes</p>
              )}
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default MovieList;