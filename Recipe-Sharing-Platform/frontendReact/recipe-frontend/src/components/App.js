import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from "react-router-dom";
import RecipesList from "./RecipesList";
import RecipeForm from "./RecipeForm";
import LoginForm from "./LoginForm";
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
              </>
            )}
          </div>
        </header>

        <div className="container mt-4">
          <Routes>
            <Route path="/" element={<RecipesList />} />
            <Route path="/login" element={<LoginForm onLogin={handleLogin} />} />
            <Route path="/add-recipe" element={isLoggedIn ? <RecipeForm /> : <Navigate to="/login" />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
};

export default App;
