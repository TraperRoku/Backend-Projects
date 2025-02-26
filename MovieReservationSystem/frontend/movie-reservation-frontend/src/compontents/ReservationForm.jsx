import React, { useState, useEffect } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import axios from "axios";
import { getUserIdFromToken } from "./jwtUtils";
import { loadStripe } from "@stripe/stripe-js";
import { Elements, useStripe, useElements, CardElement } from "@stripe/react-stripe-js";
import "./Reservation.css";
import axiosInstance from '../axios_helper';
import { getAuthToken} from '../axios_helper';

const stripePromise = loadStripe("pk_test_51QuiwPFQtGhYAs2cIZRf1vggEzWIyEKySJa6kj2k4WQgYOTIU8FxkkfcsSkHnZbDbIJV2qa7hb9UZJPteULLFTyE00d5cWPpy4");

const BASE_URL = process.env.REACT_APP_API_URL || "http://localhost:8080";

const PaymentForm = ({ reservationId, selectedSeats, id }) => {
    const stripe = useStripe();
    const elements = useElements();
    const navigate = useNavigate();
    const [error, setError] = useState(null);
    const [processing, setProcessing] = useState(false);

    const handlePayment = async (event) => {
        event.preventDefault();
        
        if (!stripe || !elements) {
            return;
        }

        setProcessing(true);
        setError(null);

        try {
            // Utwórz intencję płatności
            const response = await axiosInstance.post('/api/payments/create-intent', {
                reservationId
            });

            const clientSecret = response.data;
            const result = await stripe.confirmCardPayment(clientSecret, {
                payment_method: { card: elements.getElement(CardElement) }
            });

            if (result.error) {
                setError(result.error.message);
                // Odblokuj miejsca w przypadku błędu płatności
                await axiosInstance.post(`/api/movies/${id}/seats/unblock`, null, {
                    params: {
                        seatIds: selectedSeats.join(",")
                    }
                });
            } else {
                await axiosInstance.post(`/api/payments/confirm/${reservationId}`, {
                    status: "SUCCESS",
                    paymentIntentId: result.paymentIntent.id
                });
                
                alert("Płatność zakończona sukcesem!");
                navigate('/ticket', {
                    state: {
                      reservationId: reservationId,
                      paymentId: result.paymentIntent.id
                    }
                  });
            }
        } catch (err) {
            if (err.response?.status === 401) {
                setError("Sesja wygasła. Proszę zalogować się ponownie.");
                navigate("/login");
            } else {
                setError("Błąd podczas płatności. Spróbuj ponownie.");
                // Odblokuj miejsca w przypadku błędu
                await axiosInstance.post(`/api/movies/${id}/seats/unblock`, null, {
                    params: {
                        seatIds: selectedSeats.join(",")
                    }
                });
            }
        } finally {
            setProcessing(false);
        }
    };

    return (
        <form onSubmit={handlePayment} className="payment-form">
            <CardElement options={{
                style: {
                    base: {
                        fontSize: '16px',
                        color: '#424770',
                        '::placeholder': {
                            color: '#aab7c4',
                        },
                    },
                    invalid: {
                        color: '#9e2146',
                    },
                },
            }}/>
            {error && <p className="error-message">{error}</p>}
            <button 
                type="submit" 
                disabled={!stripe || processing}
                className="payment-button"
            >
                {processing ? "Przetwarzanie..." : "Zapłać"}
            </button>
        </form>
    );
};

const ReservationForm = () => {
    const { id } = useParams();
    const [seats, setSeats] = useState([]);
    const [selectedSeats, setSelectedSeats] = useState([]);
    const [reservationId, setReservationId] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();

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

    useEffect(() => {
        return () => {
            // Odblokuj miejsca, gdy komponent jest odmontowywany (użytkownik opuszcza stronę)
            if (selectedSeats.length > 0 && !reservationId) {
                axiosInstance.post(`/api/movies/${id}/seats/unblock`, null, {
                    params: {
                        seatIds: selectedSeats.join(",")
                    }
                }).catch(err => {
                    console.error("Błąd podczas odblokowywania miejsc:", err);
                });
            }
        };
    }, [selectedSeats, id, reservationId]);

    const toggleSeatSelection = (seatId) => {
        setSelectedSeats((prev) =>
            prev.includes(seatId) ? prev.filter((s) => s !== seatId) : [...prev, seatId]
        );
    };

    const handleReservation = async () => {
        const token = getAuthToken();
        if (!token) {
            // Redirect to login with reservation details
            navigate('/login', {
                state: {
                    from: location.pathname, // Current reservation page
                    movieScheduleId: id,
                    selectedSeats,
                },
            });
            return;
        }

        try {
            // Blokuj miejsca
            await axiosInstance.post(`/api/movies/${id}/seats/block`, null, {
                params: {
                    seatIds: selectedSeats.join(",")
                }
            });

            // Utwórz rezerwację
            const params = new URLSearchParams();
            params.append("userId", getUserIdFromToken());
            params.append("movieScheduleId", id);
            selectedSeats.forEach(seatId => params.append("seatIds", seatId));

            const response = await axiosInstance.post(
                `/api/reservation?${params.toString()}`
            );

            if (response.data) {
                setReservationId(response.data.id);
            }
        } catch (err) {
            if (err.response?.status === 401) {
                setError("Sesja wygasła. Proszę zalogować się ponownie.");
                navigate("/login");
            } else {
                setError("Błąd podczas rezerwacji.");
            }
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
                        className={`seat ${seat.status} ${selectedSeats.includes(seat.id) ? "selected" : ""}`}
                        onClick={() => toggleSeatSelection(seat.id)}
                        disabled={seat.status === "RESERVED" || seat.status === "PENDING"} 
                    >
                        {seat.seatNumber}
                    </button>
                ))}
            </div>

            <button 
                onClick={handleReservation} 
                className="reserve-button"
                disabled={selectedSeats.length === 0}
            >
                Zarezerwuj {selectedSeats.length > 0 ? `(${selectedSeats.length})` : ""}
            </button>

            {reservationId && (
                <div className="payment-container">
                    <Elements stripe={stripePromise}>
                        <PaymentForm reservationId={reservationId} selectedSeats={selectedSeats} id={id} />
                    </Elements>
                </div>
            )}
        </div>
    );
};

export default ReservationForm;