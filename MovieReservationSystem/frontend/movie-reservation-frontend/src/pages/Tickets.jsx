import React, { useEffect, useState } from 'react';
import axiosInstance from '../axios_helper';
import { getUserIdFromToken } from '../axios_helper';

const Tickets = () => {
  const [tickets, setTickets] = useState([]);
  const userId = getUserIdFromToken(); // Pobranie ID użytkownika z tokena

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
            <p>🎬 Seans: {new Date(ticket.showTime).toLocaleString()}</p>
            <p>💺 Miejsca: {ticket.seatNumbers}</p>
            <p>📄 Nr biletu: {ticket.ticketNumber}</p>
          </div>
        ))
      )}
    </div>
  );
};

export default Tickets;
