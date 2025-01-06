import React, { useState } from 'react';
import { request } from '../../axios_helper';

const AddExerciseForm = ({ workoutId, onExerciseAdded }) => {  // workoutId is already destructured here
    const [name, setName] = useState('');
    const [sets, setSets] = useState(0);
    const [reps, setReps] = useState(0);
    const [exercise, setExercises]  = useState([]);
    
        const handleAddExercise = (e) => {
            e.preventDefault();
        
            const exercise = {
                nameExercise: name,
                sets: sets,
                reps: reps,
            };
        
            // Wysyłamy dane do backendu
            request("POST", `/api/exercises/${workoutId}`, exercise)
           
                .then((response) => {
                    onExerciseAdded(response.data)
                    // Dodajemy ćwiczenie do lokalnego stanu bez ponownego pobierania danych z backendu
                    setExercises((prevExercises) => [
                        ...prevExercises,
                        response.data, // Zakładając, że odpowiedź zawiera dodane ćwiczenie
                    ]);
                    // Resetujemy formularz
                    setName('');
                    setSets(0);
                    setReps(0);
                })
                .catch((error) => {
                    console.error("Error adding exercise: ", error);
                });
            };
        
    
    return (
        <form onSubmit={handleAddExercise}>
            <div>
                <label>Exercise Name:</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Sets:</label>
                <input
                    type="number"
                    value={sets}
                    onChange={(e) => setSets(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Reps:</label>
                <input
                    type="number"
                    value={reps}
                    onChange={(e) => setReps(e.target.value)}
                    required
                />
            </div>
            <button type="submit">Add Exercise</button>
        </form>
    );
};

export default AddExerciseForm;
