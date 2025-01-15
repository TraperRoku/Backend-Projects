import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route,  Navigate } from "react-router-dom";
import RecipesList from "./RecipesList";
import RecipeForm from "./RecipeForm";
import RegisterForm from './RegisterForm'
import LoginForm from "./LoginForm";
import RecipeDetails from './RecipeDetails';
import '../index.css'
import { setAuthHeader, getAuthToken } from "../axios_helper";
import Header from './Header';

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(!!getAuthToken()); // Check if a token exists

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
      <div className="min-h-screen flex flex-col">
        <Header isLoggedIn={isLoggedIn} handleLogout={handleLogout} />
        <main className="flex-1 container mx-auto px-4 py-8">
          <Routes>
            <Route path="/" element={<RecipesList />} />
            <Route path="/login" element={<LoginForm onLogin={handleLogin} />} />
            <Route path="/register" element={<RegisterForm onRegister={handleLogin} />} />
            <Route path="/add-recipe" element={isLoggedIn ? <RecipeForm /> : <Navigate to="/login" />} />
            <Route path="/recipes/:id" element={<RecipeDetails />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
};

export default App;
