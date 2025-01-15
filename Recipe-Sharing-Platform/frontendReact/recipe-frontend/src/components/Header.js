import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { Menu, X, ChefHat, LogOut, LogIn, UserPlus, PlusCircle } from 'lucide-react';

const Header = ({ isLoggedIn, handleLogout }) => {
  const [isMenuOpen, setIsMenuOpen] = React.useState(false);
  const location = useLocation();

  const NavLink = ({ to, children, onClick }) => {
    const isActive = location.pathname === to;
    return (
      <Link
        to={to}
        onClick={onClick}
        className={`flex items-center gap-2 px-4 py-2 rounded-lg transition-colors
          ${isActive 
            ? 'bg-blue-100 text-blue-700' // Changed from primary-100 to blue-100
            : 'hover:bg-gray-100 text-gray-700 hover:text-gray-900'}`}
      >
        {children}
      </Link>
    );
  };

  const NavButton = ({ onClick, children, variant = 'default' }) => (
    <button
      onClick={onClick}
      className={`flex items-center gap-2 px-4 py-2 rounded-lg transition-colors
        ${variant === 'primary' 
          ? 'bg-blue-600 text-white hover:bg-blue-700' // Changed from primary-600 to blue-600
          : 'hover:bg-gray-100 text-gray-700 hover:text-gray-900'}`}
    >
      {children}
    </button>
  );

  return (
    <header className="sticky top-0 z-50 w-full border-b bg-white/95 backdrop-blur supports-[backdrop-filter]:bg-white/60">
      <div className="container mx-auto px-4">
        <div className="flex h-16 items-center justify-between">
          {/* Logo */}
          <Link 
  to="/" 
  className="flex items-center gap-2 text-xl font-bold text-blue-600" // Changed from primary-600 to blue-600
>
  <ChefHat className="h-6 w-6" />
  <span>Recipe Sharing</span>
</Link>

          {/* Desktop Navigation */}
          <nav className="hidden md:flex items-center gap-2">
            {isLoggedIn ? (
              <>
                <NavLink to="/add-recipe">
                  <PlusCircle className="h-4 w-4" />
                  Add Recipe
                </NavLink>
                <NavButton 
                  onClick={handleLogout} 
                  variant="primary"
                >
                  <LogOut className="h-4 w-4" />
                  Logout
                </NavButton>
              </>
            ) : (
              <>
                <NavLink to="/login">
                  <LogIn className="h-4 w-4" />
                  Login
                </NavLink>
                <NavLink to="/register">
                  <UserPlus className="h-4 w-4" />
                  Register
                </NavLink>
              </>
            )}
          </nav>

          {/* Mobile Menu Button */}
          <button 
            onClick={() => setIsMenuOpen(!isMenuOpen)}
            className="md:hidden p-2 text-gray-500 hover:text-gray-900"
          >
            {isMenuOpen ? (
              <X className="h-6 w-6" />
            ) : (
              <Menu className="h-6 w-6" />
            )}
          </button>
        </div>

        {/* Mobile Navigation */}
        {isMenuOpen && (
          <nav className="md:hidden py-4 space-y-2">
            {isLoggedIn ? (
              <>
                <NavLink 
                  to="/add-recipe"
                  onClick={() => setIsMenuOpen(false)}
                >
                  <PlusCircle className="h-4 w-4" />
                  Add Recipe
                </NavLink>
                <NavButton 
                  onClick={() => {
                    handleLogout();
                    setIsMenuOpen(false);
                  }}
                >
                  <LogOut className="h-4 w-4" />
                  Logout
                </NavButton>
              </>
            ) : (
              <>
                <NavLink 
                  to="/login"
                  onClick={() => setIsMenuOpen(false)}
                >
                  <LogIn className="h-4 w-4" />
                  Login
                </NavLink>
                <NavLink 
                  to="/register"
                  onClick={() => setIsMenuOpen(false)}
                >
                  <UserPlus className="h-4 w-4" />
                  Register
                </NavLink>
              </>
            )}
          </nav>
        )}
      </div>
    </header>
  );
};

export default Header;