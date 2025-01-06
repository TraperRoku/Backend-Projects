import React, { useState } from 'react';
import { request } from '../../axios_helper';

const AddWorkoutForm = ({ onWorkoutAdded }) => {
    const [name, setName] = useState('');
    const [date, setDate] = useState('');

    const handleAddWorkout = (e) => {
        e.preventDefault();
        const workout = {
            nameWorkout: name,
            date: date // Ensure the date is in YYYY-MM-DD format
        };
        request("POST", "/api/workouts", workout)
            .then((response) => {
                onWorkoutAdded(response.data); // Add the new workout to the list
                // Clear the form fields
                setName('');
                setDate('');
            })
            .catch((error) => {
                console.error("Error adding workout:", error);
            });
    };

    return (
        <form onSubmit={handleAddWorkout}>
            <div>
                <label>Workout Name:</label>
                <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
            </div>
            <div>
                <label>Date:</label>
                <input type="date" value={date} onChange={(e) => setDate(e.target.value)} required />
            </div>
            <button type="submit">Add Workout</button>
        </form>
    );
};

export default AddWorkoutForm;
