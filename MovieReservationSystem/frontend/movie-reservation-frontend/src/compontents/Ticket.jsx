import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { request } from '../axios_helper';
import './Ticket.css';

const Ticket = () => {
  const [ticket, setTicket] = useState(null);
  const location = useLocation();
  const navigate = useNavigate();
  const reservationId = location.state?.reservationId;

  const formatDateTime = (showTime) => {
    let date;
    if (Array.isArray(showTime) && showTime.length === 5) {
      const [year, month, day, hour, minute] = showTime;
      date = new Date(year, month - 1, day, hour, minute);
    } else {
      date = new Date(showTime);
    }

    const formattedDate = date.toLocaleDateString('pl-PL', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    });
    const formattedTime = date.toLocaleTimeString('pl-PL', {
      hour: '2-digit',
      minute: '2-digit',
    });

    return `${formattedDate} ${formattedTime}`;
  };

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

  if (!ticket) return <div className="loading">🔄 Loading ticket...</div>;

  return (
    <div className="ticket-container">
      <div className="ticket-card">
        <div className="ticket-header">
          <h2>🎟️ Ticket #{ticket.ticketNumber}</h2>
        </div>
        
        <div className="ticket-details">
          <div className="detail-item">
            <span className="detail-label">🎬 Title:</span>
            <span className="detail-value">{ticket.titleMovie}</span>
          </div>
          <div className="detail-item">
            <span className="detail-label">📅 Date and time:</span>
            <span className="detail-value">{formatDateTime(ticket.showTime)}</span>
          </div>
          <div className="detail-item">
            <span className="detail-label">💺 Seat:</span>
            <span className="detail-value">{Array.isArray(ticket.seatNumbers) ? ticket.seatNumbers.join(", ") : ticket.seatNumbers}</span>
          </div>
          <div className="detail-item">
            <span className="detail-label">🎫 Number ticket:</span>
            <span className="detail-value">{ticket.ticketNumber}</span>
          </div>
        </div>
        
        <div className="ticket-footer">
          <p>🔔 Please show your ticket before eneter</p>
        </div>
      </div>
    </div>
  );
};

export default Ticket;