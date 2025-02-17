import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import "./Reservation.css";

const BASE_URL = process.env.REACT_APP_API_URL || "http://localhost:8080";

const ReservationForm = () => {
    const { id } = useParams(); // ID seansu filmowego
    const navigate = useNavigate();
    const [seats, setSeats] = useState([]);
    const [selectedSeats, setSelectedSeats] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchSeats = async () => {
            try {
                const response = await axios.get(`${BASE_URL}/api/movies/${id}/seats`);
                setSeats(response.data);
            } catch (err) {
                setError("Nie udało się pobrać dostępnych miejsc.");
            }
        };
        fetchSeats();
    }, [id]);

    const toggleSeatSelection = (seatId) => {
        setSelectedSeats((prev) =>
            prev.includes(seatId)
                ? prev.filter((s) => s !== seatId)
                : [...prev, seatId]
        );
    };

    const handleReservation = async () => {
        try {
            const userId = localStorage.getItem("user_id"); // ID użytkownika z localStorage
            const response = await axios.post(`${BASE_URL}/api/reservation`, null, {
                params: {
                    userId: userId,
                    movieScheduleId: id,
                    seatIds: selectedSeats,
                },
            });
            alert("Rezerwacja udana!");
            navigate("/"); // Powrót do strony głównej
        } catch (err) {
            setError("Błąd podczas rezerwacji.");
        }
    };

    return (
        <div className="reservation-page">
            <h1>Rezerwacja miejsc</h1>
            {error && <p className="error-message">{error}</p>}
            <div className="seats-container">
                {seats.map((seat) => (
                    <button
                        key={seat.id}
                        className={`seat ${selectedSeats.includes(seat.id) ? "selected" : ""}`}
                        onClick={() => toggleSeatSelection(seat.id)}
                    >
                        {seat.number}
                    </button>
                ))}
            </div>
            <button onClick={handleReservation} className="reserve-button">
                Zarezerwuj {selectedSeats.length > 0 ? `(${selectedSeats.length})` : ""}
            </button>
        </div>
    );
};

export default ReservationForm;
