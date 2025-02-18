import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import "./Reservation.css";
import { getUserIdFromToken } from "./jwtUtils";

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
            const token = localStorage.getItem("auth_token");
            const userId = getUserIdFromToken();
            
            console.log('UserId from token:', userId); // Dla debugowania
            
            if (!token || !userId) {
                setError("Nie jesteś zalogowany. Zaloguj się, aby zarezerwować miejsca.");
                return;
            }
    
            const params = new URLSearchParams();
            params.append("userId", userId);
            params.append("movieScheduleId", id);
            selectedSeats.forEach(seatId => params.append("seatIds", seatId));
    
            const response = await axios.post(
                `${BASE_URL}/api/reservation?${params.toString()}`,
                null,
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );
    
            if (response.data) {
                alert("Rezerwacja została utworzona pomyślnie!");
                navigate("/");
            }
        } catch (err) {
            console.error('Błąd podczas rezerwacji:', err);
            setError("Wystąpił błąd podczas rezerwacji.");
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
