import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { getUserRole } from "../auth"; // 🟢 Importujemy poprawnie
import Home from "../pages/Home";
import Movie from "../pages/Movie";
import Reservation from "../pages/Reservation";
import Header from "./Header";
import Footer from "./Footer";
import { setAuthHeader, getAuthToken } from "../axios_helper";
import LoginForm from "./LoginForm";
import RegisterForm from "./RegisterForm";
import AddMovieForm from "../movieFolder/AddMovieForm"; 
import './global.css';


const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(!!getAuthToken());
  const [userRole, setUserRole] = useState(null);

  useEffect(() => {
    setUserRole(getUserRole()); // 🟢 Pobieramy rolę użytkownika po zalogowaniu
  }, [isLoggedIn]); 

  const handleLogin = (token) => {
    setAuthHeader(token);
    setIsLoggedIn(true);
    setUserRole(getUserRole()); // 🟢 Aktualizujemy rolę po logowaniu
  };

  const handleLogout = () => {
    setAuthHeader(null);
    setIsLoggedIn(false);
    setUserRole(null);
  };

  return (
    <Router>
      <Header isLoggedIn={isLoggedIn} userRole={userRole} onLogout={handleLogout} /> 
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/movie/:id" element={<Movie />} />
        <Route path="/reservation" element={<Reservation />} />
        <Route path="/login" element={<LoginForm onLogin={handleLogin} />} />
        <Route path="/register" element={<RegisterForm onRegister={handleLogin} />} />
        <Route path="/add-movie" element={<AddMovieForm />} />  
      </Routes>
      <Footer />
    </Router>
  );
};

export default App;
