import React from 'react';
import { Link} from 'react-router-dom';
import './Header.css';


const Header = ({ isLoggedIn, userRole, onLogout }) => {
  return (
    <header className="p-4 bg-gray-800 text-white flex justify-between">
      <Link to="/" className="text-xl font-bold">🎬 MovieApp</Link>
      <nav className="space-x-4">
        <Link to="/">Repertuar</Link>
       
        {isLoggedIn && userRole === "ADMIN" && (  // Tylko dla ADMINA
          <Link to="/add-movie" className="bg-green-500 px-3 py-1 rounded">Add Movie</Link>
        )}
        {isLoggedIn ? (
          <button onClick={onLogout} className="bg-red-500 px-3 py-1 rounded">Logout</button>
        ) : (
          <>
            <Link to="/login">Login</Link>
            <Link to="/register">Register</Link>
          </>
        )}
      </nav>
    </header>
  );
};

export default Header;