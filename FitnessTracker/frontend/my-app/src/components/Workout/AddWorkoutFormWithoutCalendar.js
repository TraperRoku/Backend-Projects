import React, { useState } from 'react';
import { request } from '../../axios_helper';

const AddWorkoutFormWithoutCalender = ({ selectedDay , onWorkoutAdded }) => {
    const [name, setName] = useState('');

 

    
    const handleAddWorkout = (e) => {
        e.preventDefault();
        const workout = {
            nameWorkout: name,
            date: selectedDay // Przekazujemy wybrany dzień
        };
        request("POST", "/api/workouts", workout)
            .then((response) => {
                onWorkoutAdded(response.data); // Przekazujemy nowy workout do komponentu nadrzędnego
                setName(''); // Czyszczenie pola po dodaniu
            })
            .catch((error) => {
                console.error("Error adding workout:", error);
            });
    };
    return (


        <form onSubmit={handleAddWorkout}>
            <div>
                <label>Workout Name:</label>
                <input 
                    type="text" 
                    value={name} 
                    onChange={(e) => setName(e.target.value)} 
                    required 
                />
            </div>
            <div>
                <label>Date:</label>
                <input 
                    type="date" 
                    value={selectedDay} // Ustawiamy datę na wybrany dzień
                    readOnly
                    disabled
                />
            </div>
            <button type="submit">Add Workout</button>
        </form>
    );
};

export default AddWorkoutFormWithoutCalender;
