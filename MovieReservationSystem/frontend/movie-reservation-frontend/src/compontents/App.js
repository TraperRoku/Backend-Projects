import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from '../pages/Home';
import Movie from '../pages/Movie';
import Reservation from '../pages/Reservation';
import Header from './Header';
import Footer from './Footer';
import { setAuthHeader, getAuthToken } from "../axios_helper";
import LoginForm from "./LoginForm";
import RegisterForm from "./RegisterForm";

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(!!getAuthToken());

  const handleLogin = (token) => {
    setAuthHeader(token);
    setIsLoggedIn(true);
  };

  const handleLogout = () => {
    setAuthHeader(null);
    setIsLoggedIn(false);
  };

  return (
    <Router>
      <Header isLoggedIn={isLoggedIn} onLogout={handleLogout} />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/movie/:id" element={<Movie />} />
        <Route path="/reservation" element={<Reservation />} />
        <Route
          path="/login"
          element={<LoginForm onLogin={handleLogin} />}
        />
        <Route
          path="/register"
          element={<RegisterForm onRegister={handleLogin} />}
        />
       
      </Routes>
      <Footer />
    </Router>
  );
};

export default App;