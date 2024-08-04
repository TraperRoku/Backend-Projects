# To-Do List API with Authentication
This project is a RESTful API for managing a to-do list, built using Spring Boot.
The application includes user authentication and authorization using Spring Security. 
# Features
### User Registration: 
- Users can register with a username and password.
### Authentication and Authorization: 
- The API uses Basic Authentication to secure endpoints. Different roles are supported for different levels of access.
### Task Managment:
- Create Tasks
- Get all Tasks
- Update Tasks
- Delete Tasks
- Change Status Tasks
### Role-based Access Control:
- Ensures that only authorized users can perform certain actions.
# Getting Started
- I use java 22 and Maven 3.3.2
### Clone :
- git clone https://github.com/TraperRoku/Backend-Projects.git
# API Endpoints
### User Registration
POST /api/auth/register: Register a new user.
### Task Management
- GET /tasks/user/{userId}: Retrieve tasks for a specific user.
- POST /tasks/create: Create a new task.
- PUT /tasks/update/{id}: Update an existing task.
- DELETE /tasks/delete/{id}: Delete a task by ID.

You can test my app in Postman 
![image](https://github.com/user-attachments/assets/8a1d7507-079d-4a73-8487-47b13c185826)
![image](https://github.com/user-attachments/assets/ee504be6-f255-446d-991f-6266fec64e24)
