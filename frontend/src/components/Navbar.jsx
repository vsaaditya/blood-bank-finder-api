import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FaDroplet, FaSignOutAlt, FaHome, FaTint, FaHospital, FaUser } from 'react-icons/fa';
import { useAuth } from '../context/AuthContext';

export const Navbar = () => {
  const { user, logout, isAuthenticated } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="bg-blood-900 text-white shadow-lg">
      <div className="max-w-7xl mx-auto px-4">
        <div className="flex justify-between items-center h-16">
          <Link to="/" className="flex items-center gap-2 text-2xl font-bold hover:text-blood-100">
            <FaDroplet className="text-blood-500" />
            Blood Bank Finder
          </Link>

          <div className="flex items-center gap-6">
            {isAuthenticated ? (
              <>
                <div className="flex gap-6">
                  <Link to="/dashboard" className="hover:text-blood-100 flex items-center gap-2">
                    <FaHome /> Dashboard
                  </Link>
                  <Link to="/search" className="hover:text-blood-100 flex items-center gap-2">
                    <FaTint /> Search Blood
                  </Link>
                  <Link to="/blood-banks" className="hover:text-blood-100 flex items-center gap-2">
                    <FaHospital /> Banks
                  </Link>
                  <Link to="/donors" className="hover:text-blood-100 flex items-center gap-2">
                    <FaUser /> Donors
                  </Link>
                </div>
                <div className="flex items-center gap-4 border-l pl-6 border-blood-700">
                  <span className="text-sm">Welcome, {user?.username}</span>
                  <button
                    onClick={handleLogout}
                    className="btn-primary flex items-center gap-2"
                  >
                    <FaSignOutAlt /> Logout
                  </button>
                </div>
              </>
            ) : (
              <div className="flex gap-4">
                <Link to="/login" className="btn-secondary">
                  Login
                </Link>
                <Link to="/register" className="btn-primary">
                  Register
                </Link>
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};
