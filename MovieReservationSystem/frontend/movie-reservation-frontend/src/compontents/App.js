import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { getUserRole } from "../auth"; 
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
import Ticket from "./Ticket";
import Tickets from "../pages/Tickets";



const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(!!getAuthToken());
  const [userRole, setUserRole] = useState(null);

  useEffect(() => {
    setUserRole(getUserRole()); 
  }, [isLoggedIn]); 

  const handleLogin = (token) => {
    setAuthHeader(token);
    setIsLoggedIn(true);
    setUserRole(getUserRole()); 
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
        <Route path="/reservation/:id" element={<Reservation />} /> 
        <Route path="/login" element={<LoginForm onLogin={handleLogin} />} />
        <Route path="/register" element={<RegisterForm onRegister={handleLogin} />} />
        <Route path="/add-movie" element={<AddMovieForm />} />  
        <Route path="/ticket" element={<Ticket />} />
        <Route path="/tickets" element={<Tickets />} />
   
      </Routes>
      <Footer />
    </Router>
  );
};

export default App;
