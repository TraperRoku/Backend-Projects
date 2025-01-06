import React from 'react';
import { request } from '../../axios_helper';
import AddWorkoutForm from './AddWorkoutForm';
import WeekView from './WeekView';
import DayView from './DayView';
import './WorkoutApp.css'

export default class WorkoutApp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            exercises: [], // cwiczenia
            currentWeekStart: this.getStartOfWeek(new Date()), // Początkowa data tygodnia
            selectedDay: null, // Zmienna do trzymania wybranego dnia
        };
    }

    
        componentDidMount() {
            this.fetchWorkouts(this.state.currentWeekStart);
        }
    
        getStartOfWeek(date) {
            const day = date.getDay(); // 0 (niedziela) do 6 (sobota)
       
            return new Date(date.setDate(date.getDate() - day + 1)); // Zawsze ustawia datę na poniedziałek
        }
        
        fetchWorkouts = (weekStart) => {
            const formattedDate = weekStart.toISOString().split('T')[0]; // yyyy-mm-dd
            request("GET", `/api/workouts?weekStart=${formattedDate}`, {})
            .then((response) => {
                if (Array.isArray(response.data)) {
                    this.setState({ data: response.data });
                } else {
                    console.error("Expected an array, but got:", response.data);
                }
            });
        }


        fetchComments = (workoutId) => {
        request("")
        }


      fetchExercises = (workoutId) => {
    request("GET", `/api/exercises/workout/${workoutId}`, {})
        .then((response) => {
            
            if (response.data) {
                this.setState((prevState) => ({
                    exercises: [...prevState.exercises, ...response.data],
                }));
            }
        })
        .catch((error) => {
            console.error('Error fetching exercises:', error);
        });
};

        
    

    //Wyswietlanie na stronie dni
        handleWorkoutAdded = (newWorkout) => {
         
            const workoutDate = new Date(newWorkout.date); 
        
            newWorkout.date = workoutDate.toISOString(); 
        
            // Przesyłamy nowy obiekt do bazy danych
            this.setState((prevState) => ({
                data: [...prevState.data, newWorkout]
            }));
        };
        
         handleBackToWeekView = () => {
        this.setState({ selectedDay: null });
         this.setState({exercises : [] });
    };

        handleExerciseAdded = (newExercise) => {
            this.setState((prevState) => ({
                exercises: [...prevState.exercises, newExercise]
            }));
        };


    
    
        goToPreviousWeek = () => {
            const prevWeekStart = new Date(this.state.currentWeekStart);
            prevWeekStart.setDate(prevWeekStart.getDate() - 7);
            this.setState({ currentWeekStart: prevWeekStart }, () => {
                this.fetchWorkouts(this.state.currentWeekStart);
            });
        };
    
        goToNextWeek = () => {
            const nextWeekStart = new Date(this.state.currentWeekStart);
            nextWeekStart.setDate(nextWeekStart.getDate() + 7);
            this.setState({ currentWeekStart: nextWeekStart }, () => {
                this.fetchWorkouts(this.state.currentWeekStart);
            });
        };

        handleDayClick = (date) => {
            this.setState({ selectedDay: date }, () => {
                // Wywołaj fetchExercises dla każdego treningu wybranego dnia
                const dayWorkouts = this.state.data.filter(
                    (workout) => new Date(workout.date).toDateString() === new Date(date).toDateString()
                );
        
                dayWorkouts.forEach((workout) => {
                    this.fetchExercises(workout.id); // Pobieranie ćwiczeń dla każdego treningu
                });
            });
        };

        handleEditWorkout = (updatedWorkout) => {
            request("PUT", `/api/workouts/${updatedWorkout.id}`, updatedWorkout)
              .then((response) => {
                this.setState((prevState) => ({
                  data: prevState.data.map((workout) =>
                    workout.id === updatedWorkout.id ? { ...workout, ...updatedWorkout } : workout
                  ),
                }));
              })
              .catch((error) => {
                console.error("Error updating workout:", error);
              });
          };
          

        handleDeleteWorkout = (workoutId) => {
            request("DELETE", `/api/workouts/${workoutId}`, {})
            .then(() => {
                this.setState((prevState) => ({
                    data: prevState.data.filter(workout => workout.id !== workoutId),
                    exercises: prevState.exercises.filter(exercise => exercise.workoutId !== workoutId)
                }));
            })
            .catch((error) => {
                console.error('Error deleting workout:', error);
            });
        };


        handleDeleteExercise = (exerciseId) => {
            if (!exerciseId) {
              console.error("Invalid exercise ID for delete:", exerciseId);
              return;
            }
          
            request("DELETE", `/api/exercises/${exerciseId}`, {})
              .then(() => {
                this.setState((prevState) => ({
                  exercises: prevState.exercises.filter((exercise) => exercise.id !== exerciseId),
                }));
              })
              .catch((error) => {
                console.error("Error deleting exercise:", error);
              });
          };
          


          handleEditExercise = (updatedExercise) => {
            if (!updatedExercise.id) {
              console.error("Invalid exercise ID for update:", updatedExercise);
              return;
            }
          
            request("PUT", `/api/exercises/${updatedExercise.id}`, updatedExercise)
              .then((response) => {
                this.setState((prevState) => ({
                  exercises: prevState.exercises.map((exercise) =>
                    exercise.id === updatedExercise.id ? { ...exercise, ...response.data } : exercise
                  ),
                }));
              })
              .catch((error) => {
                console.error("Error updating exercise:", error);
              });
          };



        



    render() {
        return (
            <div className='row justify-content-md-center workoutDayTime'>
                <div className='col-10'>
                    <div className='card' style={{ width: "100%", height: "100%", border: "3px solid #022", borderRadius: "8px" }}>
                        <div className='card-body'>
                            <h2 className='card-title'>Workout Week</h2>
    
                           
                            { !this.state.selectedDay && (
        <div>
           

            <AddWorkoutForm onWorkoutAdded={this.handleWorkoutAdded} selectedDay={this.state.selectedDay}  />
        </div>
    )
}
                            {this.state.selectedDay ? (
                                <DayView
                                selectedDay={this.state.selectedDay}
                                workouts={this.state.data}
                                onBackToWeekView={this.handleBackToWeekView}
                                onEditWorkout={this.handleEditWorkout}
                                onDeleteWorkout={this.handleDeleteWorkout}
                                onWorkoutAdded={this.handleWorkoutAdded}
                                exercises={this.state.exercises}
                                handleExerciseAdded={this.handleExerciseAdded}
                                onDeleteExercise={this.handleDeleteExercise}
                                onEditExercise={this.handleEditExercise}
                                />
                            ) : (
                                <WeekView
                                    workouts={this.state.data}
                                    onDayClick={this.handleDayClick}
                                />
                            )}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
    
}

