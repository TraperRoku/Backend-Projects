# 🎬 Cinema Reservation System  

## 🚀 About the Project  
This project is a **Cinema Ticket Booking System**, allowing users to:  
- Browse movies and showtimes  
- Select and reserve seats  
- Make payments using **Stripe**  
- Generate and view tickets  
- Manage movies (Admin Panel)  

It provides a **full-stack web application** built with:  
- **Frontend:** React.js  
- **Backend:** Spring Boot  
- **Database:** MySQL  
- **Payments:** **Stripe API**  

---

## 🔥 Key Features  
### 🎥 Movie Management  
- ✅ **Admins** can add, update, and remove movies.  
- ✅ Each movie has multiple showtimes.  

### 🎟 Seat Reservation System  
- ✅ Users can browse available seats for a selected showtime.  
- ✅ Seat availability is updated in real-time.  
- ✅ Reserved seats are locked for a period before payment.  

### 💳 Secure Payments with Stripe  
- ✅ Payments are handled using Stripe API.
- ✅ After successful payment, the system generates a ticket.
- ✅ If payment fails, the seat is released.

### 🎫 Ticket System  
- ✅ Users can view and manage their booked tickets.  

### 👤 Authentication & Authorization  
- ✅ **Users** can register and log in.  
- ✅ **Admins** have special privileges to manage movies.  

---
### ⚙️ Backend  
- Java + Spring Boot  
- Spring Security (Authentication & JWT)  
- MySQL (Database)  
- Stripe API (for Payments)
- 
## 🛠 Tech Stack  
### 💻 Frontend  
- React.js  
- Axios (for API requests)  

### 🔗 API & Tools  
- **RESTful API** for communication between frontend and backend  
- **JWT Authentication** for user sessions  
- **Axios** for handling HTTP requests  

---

## 🏆 Challenges & Solutions  

### 1️⃣ Handling Seat Availability in Real-Time  
**Problem:** Users could try to reserve the same seat at the same time.
**Solution:** Added a seat locking system to avoid duplicate bookings.

### 2️⃣ Payment & Ticket Generation with Stripe  
**Problem:** Payments needed to be safe and well-integrated. 
**Solution:** Used Stripe Payment Intents API to confirm payments before ticket creation.

### 3️⃣ Preventing Expired Reservations  
**Problem:** Users could block seats but not pay. 
**Solution:**  Seats are automatically released if no payment is made within a time limit (10 min).
---
## 🎯 Why This Project is Impressive?  
✅ **Real-world use case** – Simulates a cinema booking system.
✅ **Secure payment integration** – Implemented **Stripe API** effectively.  
✅ **Advanced backend handling** – Managing reservations, payments, and seat locks.  

This project showcases my ability to work with **full-stack development**, integrate **secure payment systems**, and solve real-world problems in a structured and scalable way.  

---

## 📸 Screenshots  
Adding a movie as an Admin
![image](https://github.com/user-attachments/assets/52b4881c-76d6-4864-b461-63ca6e0b276e)
![image](https://github.com/user-attachments/assets/0f9e6e35-0829-41ce-9450-f2e8ea7935d8)
Register a User
![image](https://github.com/user-attachments/assets/374e38a8-7b0e-43bd-bc4d-e3098cc89408)
Login Page
![image](https://github.com/user-attachments/assets/01a3a0a3-283f-485f-bbd0-f89a21fa3331)

Reserving a Ticket
![image](https://github.com/user-attachments/assets/ec350b9d-cb3d-4ac9-849c-2426401ef7c2)

![image](https://github.com/user-attachments/assets/1ee4cc4e-84b5-4781-8f8a-22ce4aa7cfc6)
![image](https://github.com/user-attachments/assets/b1b216d8-d847-41f5-9108-1cd0d9f9e7b3)
![image](https://github.com/user-attachments/assets/4523bc0f-df82-4365-910e-57c4903460b0)



My Tickets
![image](https://github.com/user-attachments/assets/057a2471-e9cf-4564-9a5b-272a1a72d36a)

Find Movie
![image](https://github.com/user-attachments/assets/b507892b-4d8a-4ef2-b12c-3d5260ea7a3b)
DataBase
Seat
![image](https://github.com/user-attachments/assets/6f2177f1-973d-4d45-acb3-362c4e6e3b61)

Reservation
![image](https://github.com/user-attachments/assets/44ff397f-6bfe-489b-a9cb-67f247167e81)
Payment
![image](https://github.com/user-attachments/assets/4b420321-0da2-4d64-b1d6-34643d831592)
Ticket
![image](https://github.com/user-attachments/assets/d5f15042-f798-451d-8fce-37e86cb87749)
Movie
![image](https://github.com/user-attachments/assets/b7e90d7a-1c15-4d89-ba3c-f975b6484c66)
MovieSchedule
![image](https://github.com/user-attachments/assets/a725d140-5a1b-402f-a6fd-997e85428732)
User
![image](https://github.com/user-attachments/assets/818bc3c7-07f2-4168-8860-0083b09d639a)






