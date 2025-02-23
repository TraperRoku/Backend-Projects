import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { getUserRole } from '../auth';
import { Plus, X } from 'lucide-react';
import './AddMovieForm.css';

const AddMovieForm = () => {
    const [formData, setFormData] = useState({
        title: '',
        description: '',
        genre: [],
        duration: '',
        posterUrl: '',
        startDate: '', 
        endDate: '',
        showTime: ''
    });

    const [newGenre, setNewGenre] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const [userRole, setUserRole] = useState(null);

    useEffect(() => {
        const role = getUserRole();
        setUserRole(role);

        if (role !== 'ADMIN') {
            navigate('/');
        }
    }, [navigate]);

    const handleAddGenre = (e) => {
        e.preventDefault();
        if (newGenre.trim()) {
            setFormData(prev => ({
                ...prev,
                genre: [...prev.genre, newGenre.trim()]
            }));
            setNewGenre('');
        }
    };

    const handleRemoveGenre = (indexToRemove) => {
        setFormData(prev => ({
            ...prev,
            genre: prev.genre.filter((_, index) => index !== indexToRemove)
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        if (formData.genre.length === 0) {
            setError('Please add at least one genre');
            return;
        }

        try {
            const token = localStorage.getItem('auth_token');
            if (!token) {
                setError('Unauthorized: No token found.');
                return;
            }

            await axios.post('http://localhost:8080/api/movies', formData, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                }
            });

            navigate('/movies');
        } catch (err) {
            console.error("Error adding movie:", err.response?.data || err.message);
            setError(err.response?.data?.message || 'Failed to add movie');
        }
    };

    if (userRole !== 'ADMIN') {
        return (
            <div className="add-movie-form">
                <p className="error-message">🔒 Access Denied - Admin privileges required</p>
            </div>
        );
    }

    return (
        <div className="add-movie-form">
            <h2>Add New Movie</h2>
            {error && <div className="error-message">{error}</div>}
            
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Title</label>
                    <input
                        type="text"
                        value={formData.title}
                        onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                        placeholder="Enter movie title"
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Description</label>
                    <textarea
                        value={formData.description}
                        onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                        placeholder="Enter movie description"
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Genres</label>
                    <div className="genre-input-container">
                        <input
                            type="text"
                            value={newGenre}
                            onChange={(e) => setNewGenre(e.target.value)}
                            placeholder="Enter a genre"
                            onKeyPress={(e) => {
                                if (e.key === 'Enter') {
                                    handleAddGenre(e);
                                }
                            }}
                        />
                        <button
                            type="button"
                            onClick={handleAddGenre}
                            className="genre-add-button"
                        >
                            <Plus size={20} />
                        </button>
                    </div>
                    <div className="genre-tags">
                        {formData.genre.map((genre, index) => (
                            <span key={index} className="genre-tag">
                                {genre}
                                <button
                                    type="button"
                                    onClick={() => handleRemoveGenre(index)}
                                    className="genre-remove-button"
                                >
                                    <X size={14} />
                                </button>
                            </span>
                        ))}
                    </div>
                </div>

                <div className="form-group">
                    <label>Duration (minutes)</label>
                    <input
                        type="number"
                        value={formData.duration}
                        onChange={(e) => setFormData({ ...formData, duration: e.target.value })}
                        placeholder="Enter duration in minutes"
                        min="1"
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Poster URL</label>
                    <input
                        type="url"
                        value={formData.posterUrl}
                        onChange={(e) => setFormData({ ...formData, posterUrl: e.target.value })}
                        placeholder="Enter poster URL"
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Start Date:</label>
                    <input 
                        type="date" 
                        value={formData.startDate} 
                        onChange={(e) => setFormData({ ...formData, startDate: e.target.value })} 
                        required 
                    />
                </div>

                <div className="form-group">
                    <label>End Date:</label>
                    <input 
                        type="date" 
                        value={formData.endDate} 
                        onChange={(e) => setFormData({ ...formData, endDate: e.target.value })} 
                        required 
                    />
                </div>

                <div className="form-group">
                    <label>Show Time::</label>
                    <input 
                        type="time" 
                        value={formData.showTime} 
                        onChange={(e) => setFormData({ ...formData, showTime: e.target.value })} 
                        required 
                    />
                </div>

                <button type="submit">
                    Add Movie
                </button>
            </form>
        </div>
    );
};

export default AddMovieForm;
