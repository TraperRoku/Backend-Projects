import React, { useEffect, useState } from 'react';
import axiosInstance from '../axios_helper';
import { getUserIdFromToken } from '../axios_helper';
import './Tickets.css';

const Tickets = () => {
  const [tickets, setTickets] = useState([]);
  const userId = getUserIdFromToken(); 


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
    const fetchTickets = async () => {
      try {
        const response = await axiosInstance.get(`/api/tickets/user/${userId}`);
        setTickets(response.data);
      } catch (error) {
        console.error("❌ Błąd pobierania biletów:", error);
      }
    };

    if (userId) {
      fetchTickets();
    }
  }, [userId]);

  return (
    <div className="ticket-list">
      <h1>🎟 Moje Bilety</h1>
      {tickets.length === 0 ? (
        <p>Brak biletów</p>
      ) : (
        tickets.map((ticket) => (
          <div key={ticket.ticketNumber} className="ticket-card">
            <h2>{ticket.movieTitle}</h2>
            <p>🎬 Seans: {formatDateTime(ticket.showTime)}</p>
            <p>💺 Miejsca: {ticket.seatNumbers}</p>
            <p>📄 Nr biletu: {ticket.ticketNumber}</p>
          </div>
        ))
      )}
    </div>
  );
};

export default Tickets;