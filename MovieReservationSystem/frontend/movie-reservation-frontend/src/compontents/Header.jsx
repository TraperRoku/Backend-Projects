import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Header = ({ isLoggedIn, onLogout }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    onLogout(); 
    navigate("/"); 
  };

  return (
    <header className="bg-blue-600 text-white p-4">
      <nav className="container mx-auto flex justify-between items-center">
        <div className="flex space-x-4">
          <Link to="/" className="hover:text-blue-200">Home</Link>
          <Link to="/movies" className="hover:text-blue-200">Movies</Link>
        </div>
        <div className="flex space-x-4">
          {isLoggedIn ? (
            <button onClick={handleLogout} className="hover:text-blue-200">
              Logout
            </button>
          ) : (
            <>
              <Link to="/login" className="hover:text-blue-200">Login</Link>
              <Link to="/register" className="hover:text-blue-200">Register</Link>
            </>
          )}
        </div>
        
      </nav>
    </header>
  );
};

export default Header;