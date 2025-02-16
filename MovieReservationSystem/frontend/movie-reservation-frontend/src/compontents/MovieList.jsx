import React, { useEffect, useState } from 'react';
import { fetchMovies } from '../services/api';
import { Link } from 'react-router-dom';
import './Movie.css';

const MovieList = () => {
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    fetchMovies().then((response) => setMovies(response.data));
  }, []);

  return (
    <div>
      {movies.map((movie) => (
        <div key={movie.id}>
          <h2>
            <Link to={`/movie/${movie.id}`}>{movie.title}</Link>
          </h2>
          <p>{movie.description}</p>
        </div>
      ))}
    </div>
  );
};

export default MovieList;