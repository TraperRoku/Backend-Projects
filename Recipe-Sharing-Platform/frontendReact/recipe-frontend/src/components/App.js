import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from "react-router-dom";
import RecipesList from "./RecipesList";
import RecipeForm from "./RecipeForm";
import RegisterForm from './RegisterForm'
import LoginForm from "./LoginForm";
import RecipeDetails from './RecipeDetails';
import { setAuthHeader, getAuthToken } from "../axios_helper";

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
      <div>
        <header className="navbar navbar-light bg-light">
          <Link className="navbar-brand" to="/">Recipe Sharing</Link>
          <div>
            {isLoggedIn ? (
              <>
                <button className="btn btn-primary me-2" onClick={handleLogout}>Logout</button>
                <Link className="btn btn-success" to="/add-recipe">Add Recipe</Link>
              </>
            ) : (
              <>
                <Link className="btn btn-primary me-2" to="/login">Login</Link>
                <Link className="btn btn-primary me-2" to="/register">Register</Link>
              </>
            )}
          </div>
        </header>

        <div className="container mt-4">
          <Routes>
            <Route path="/" element={<RecipesList />} />
            <Route path="/login" element={<LoginForm onLogin={handleLogin} />} />
            <Route path="/register" element={<RegisterForm onRegister={handleLogin} />} />
            <Route path="/add-recipe" element={isLoggedIn ? <RecipeForm /> : <Navigate to="/login" />} />
            <Route path="/recipes/:id" element={<RecipeDetails />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
};

export default App;
