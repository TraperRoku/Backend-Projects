import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { request } from '../axios_helper';
import './Ticket.css';

const Ticket = () => {
  const [ticket, setTicket] = useState(null);
  const location = useLocation();
  const navigate = useNavigate();
  const reservationId = location.state?.reservationId;

  useEffect(() => {
    const fetchTicket = async () => {
      try {
        const response = await request('GET', `/api/tickets/reservation/${reservationId}`);
        console.log("🎟 Otrzymano bilet:", response);
        setTicket(response.data);
      } catch (error) {
        console.error('❌ Error fetching ticket:', error);
        navigate('/');
      }
    };

    if (reservationId) {
      fetchTicket();
    }
  }, [reservationId, navigate]);

  const formatDateTime = (showTime) => {
    if (Array.isArray(showTime) && showTime.length === 5) {
      const [year, month, day, hour, minute] = showTime;
      return new Date(year, month - 1, day, hour, minute);
    }
    return new Date(showTime);
  };

  if (!ticket) return <div className="loading">🔄 Ładowanie biletu...</div>;

  return (
    <div className="ticket-container">
      <div className="ticket-card">
        <div className="ticket-header">
          <h2>🎟️ Bilet #{ticket.ticketNumber}</h2>
        </div>
        
        <div className="ticket-details">
          <div className="detail-item">
            <span className="detail-label">📅 Data:</span>
            <span className="detail-value">{formatDateTime(ticket.showTime).toLocaleDateString()}</span>
          </div>
          <div className="detail-item">
            <span className="detail-label">⏰ Godzina:</span>
            <span className="detail-value">{formatDateTime(ticket.showTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</span>
          </div>
          <div className="detail-item">
            <span className="detail-label">💺 Miejsca:</span>
            <span className="detail-value">{Array.isArray(ticket.seatNumbers) ? ticket.seatNumbers.join(", ") : ticket.seatNumbers}</span>
          </div>
          <div className="detail-item">
            <span className="detail-label">🎫 Numer biletu:</span>
            <span className="detail-value">{ticket.ticketNumber}</span>
          </div>
        </div>
        
        <div className="ticket-footer">
          <p>🔔 Proszę okazać bilet przy wejściu do kina</p>
        </div>
      </div>
    </div>
  );
};

export default Ticket;
