import React, { useState } from 'react';

const WeekView = ({ workouts, onDayClick }) => {
    const getStartOfWeek = (date) => {
        const day = date.getDay(); // 0 (niedziela) do 6 (sobota)
        const difference = day === 0 ? -6 : 1 - day; // Przesunięcie do poniedziałku
        return new Date(date.setDate(date.getDate() + difference)); // Ustawienie daty na poniedziałek
    };

    // Ustawienie początkowego stanu tygodnia na obecny tydzień
    const [currentWeekStart, setCurrentWeekStart] = useState(getStartOfWeek(new Date()));

    // Funkcja do generowania tablicy dat tygodnia
    const getWeekDates = (weekStart) => {
        return Array.from({ length: 7 }, (_, i) => {
            const date = new Date(weekStart);
            date.setDate(weekStart.getDate() + i);
            return date;
        });
    };

    // Obliczanie dat tygodnia na podstawie aktualnego tygodnia
    const weekDates = getWeekDates(currentWeekStart);

    // Struktura dni tygodnia
    const weekDays = weekDates.reduce((acc, date) => {
        const fullDate = date.toISOString().split('T')[0]; // YYYY-MM-DD
        const day = date.toLocaleDateString('en-US', { weekday: 'long' });
        acc[fullDate] = { day, workouts: [] };
        return acc;
    }, {});

    // Dodanie ćwiczeń do odpowiednich dni z przesunięciem o 1 dzień
    workouts.forEach((item) => {
        const workoutDate = new Date(item.date);
        workoutDate.setDate(workoutDate.getDate() + 1); // Przesunięcie daty ćwiczenia o 1 dzień
        const workoutDateStr = workoutDate.toISOString().split('T')[0]; // YYYY-MM-DD
        if (weekDays[workoutDateStr]) {
            weekDays[workoutDateStr].workouts.push(item.nameWorkout);
        }
    });

    // Funkcje zmieniające tydzień
    const goToPreviousWeek = () => {
        const prevWeekStart = new Date(currentWeekStart);
        prevWeekStart.setDate(currentWeekStart.getDate() - 7);
        setCurrentWeekStart(prevWeekStart);
    };

    const goToNextWeek = () => {
        const nextWeekStart = new Date(currentWeekStart);
        nextWeekStart.setDate(nextWeekStart.getDate() + 7);
        setCurrentWeekStart(nextWeekStart);
    };

    return (
        <div className="week-container">
            <div className="week-navigation">
                <button onClick={goToPreviousWeek}>Previous Week</button>
                <button onClick={goToNextWeek}>Next Week</button>
            </div>
            {Object.keys(weekDays).map(date => (
                <div key={date} className="day-card">
                    <button onClick={() => onDayClick(date)}>
                        {weekDays[date].day} ({date})
                    </button>
                    <ul>
                        {weekDays[date].workouts.length > 0 ? (
                            weekDays[date].workouts.map((workout, index) => (
                                <li key={index}>{workout}</li>
                            ))
                        ) : (
                            <li>No workout scheduled</li>
                        )}
                    </ul>
                </div>
            ))}
        </div>
    );
};

export default WeekView;
