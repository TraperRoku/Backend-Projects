import React, { useState } from 'react';
import AddWorkoutFormWithoutCalender from './AddWorkoutFormWithoutCalendar';
import AddExerciseForm from './AddExerciseForm';
import AddComment from './AddComment';
import './DayView.css'

const DayView = ({
  selectedDay,
  workouts,
  onBackToWeekView,
  onEditWorkout,
  onDeleteWorkout,
  onWorkoutAdded,
  exercises,
  handleExerciseAdded,
  onDeleteExercise,
  onEditExercise,


}) => {
  // Function to normalize the date to start at 00:00:00
  const normalizeDate = (date) => {
    const newDate = new Date(date);
    newDate.setHours(0, 0, 0, 0); // Set time to 00:00:00
    return newDate;
  };

  // Convert selectedDay to start of the day (00:00:00)
  const normalizedSelectedDay = normalizeDate(selectedDay);

  // Filter workouts based on the selected day
  const dayWorkouts = workouts.filter((item) => {
    const workoutDate = normalizeDate(item.date); // Normalize the date
    return workoutDate.getTime() === normalizedSelectedDay.getTime(); // Compare only the dates
  });

  // Function to get exercises for a specific workout
  const getExercisesForWorkout = (workoutId) => {
    return exercises.filter((exercise) => exercise.workoutId === workoutId);
  };




  // Handle delete button click
  const handleDeleteClick = (workoutId) => {
    onDeleteWorkout(workoutId); // Pass the workout ID for deletion
  };


 

  const [editingWorkoutId, setEditingWorkoutId] = useState(null);
  const [editedWorkoutName, setEditedWorkoutName] = useState('');

  const handleEditClick = (workout) => {
    setEditingWorkoutId(workout.id);
    setEditedWorkoutName(workout.nameWorkout);
  };

   const handleCancelEdit = () => {
    setEditingWorkoutId(null);
    setEditedWorkoutName('');
  };
  const handleSaveEdit = () => {
    onEditWorkout({
      id: editingWorkoutId,
      nameWorkout: editedWorkoutName,
    });
    setEditingWorkoutId(null);
    setEditedWorkoutName('');
  };
  const [editingExerciseId, setEditingExerciseId] = useState(null);
  const [editedExercise, setEditedExercise] = useState({
    nameExercise: '',
    sets: '',
    reps: ''
  });


  const handleEditExerciseClick = (exercise) => {
    console.log("Clicked exercise:", exercise);
    setEditingExerciseId(exercise.id);
    setEditedExercise({
      nameExercise: exercise.nameExercise,
      sets: exercise.sets,
      reps: exercise.reps,
    });
  };
  
  const handleCancelExerciseEdit = () => {
    setEditingExerciseId(null);
    setEditedExercise({
      nameExercise: '',
      sets: '',
      reps: ''
    });
  };
  
  const handleSaveExerciseEdit = () => {

    console.log("Saving edited exercise:", {
      id: editingExerciseId,
      ...editedExercise,
  });

  if (!editingExerciseId) {
      console.error("No exercise ID provided!");
      return;
  }

    onEditExercise({
      id: editingExerciseId,
      nameExercise: editedExercise.nameExercise,
      sets: editedExercise.sets,
      reps: editedExercise.reps
    });
    setEditingExerciseId(null);
    setEditedExercise({
      nameExercise: '',
      sets: '',
      reps: ''
    });
  };

 



  return (
    <div className="single-day-view">
      <button onClick={onBackToWeekView}>Back to Week View</button>

      <h3>
        {new Date(normalizedSelectedDay).toLocaleDateString('en-US', {
          weekday: 'long',
        })}{' '}
        ({selectedDay})
      </h3>

      {/* Form for adding a workout */}
      <AddWorkoutFormWithoutCalender
        selectedDay={selectedDay} // Passing selectedDay from the parent component
        onWorkoutAdded={onWorkoutAdded} // Function to update state after adding a workout
      />

      <ul>
        {dayWorkouts.length > 0 ? (
          dayWorkouts.map((workout, index) => {
          

            return (
              <li key={index}>
                {editingWorkoutId === workout.id ? (
                <div>
                  <input
                    type="text"
                    value={editedWorkoutName}
                    onChange={(e) => setEditedWorkoutName(e.target.value)}
                  />
                  <button onClick={handleSaveEdit}>Save</button>
                  <button onClick={handleCancelEdit}>Cancel</button>
                </div>
              ) : (
                <>
                  <strong>{workout.nameWorkout}</strong>
                  <button onClick={() => handleEditClick(workout)}>Edit</button>
                  <button onClick={() => handleDeleteClick(workout.id)}>Delete</button>
                </>
              )}


                <AddComment workoutId={workout.id} />

               {/* Wyświetlanie ćwiczeń dla danego treningu */}
                                        <ul>            
                                        {getExercisesForWorkout(workout.id).length > 0 ? (
                              getExercisesForWorkout(workout.id).map((exercise, i) => (
                                <li key={i}>
                                  {editingExerciseId === exercise.id ? (
                                    <div>
                                      <input
                                        type="text"
                                        value={editedExercise.nameExercise}
                                        onChange={(e) =>
                                          setEditedExercise((prev) => ({
                                            ...prev,
                                            nameExercise: e.target.value,
                                          }))
                                        }
                                      />
                                      <input
                                        type="number"
                                        value={editedExercise.sets}
                                        onChange={(e) =>
                                          setEditedExercise((prev) => ({
                                            ...prev,
                                            sets: e.target.value,
                                          }))
                                        }
                                      />
                                      <input
                                        type="number"
                                        value={editedExercise.reps}
                                        onChange={(e) =>
                                          setEditedExercise((prev) => ({
                                            ...prev,
                                            reps: e.target.value,
                                          }))
                                        }
                                      />
                                      <button onClick={handleSaveExerciseEdit}>Save</button>
                                      <button onClick={handleCancelExerciseEdit}>Cancel</button>
                                    </div>
                                  ) : (
                                    <>
                                      {exercise.nameExercise} - {exercise.sets} sets x {exercise.reps} reps
                                      <button onClick={() => handleEditExerciseClick(exercise)}>
                                        Edit
                                      </button>
                                      <button onClick={() => onDeleteExercise(exercise.id)}>Delete</button>
                                    </>
                                  )}
                                </li>
                              ))
                            ) : (
                              <li>No exercises added</li>
                            )}
</ul>
                {/* Form to add exercises */}
                <AddExerciseForm workoutId={workout.id} onExerciseAdded={handleExerciseAdded} />
              </li>
            );
          })
        ) : (
          <li>No workout scheduled</li>
        )}
      </ul>
    </div>
  );
};

export default DayView;
