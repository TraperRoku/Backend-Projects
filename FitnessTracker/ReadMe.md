# FitnessTracker
This project is a workout management system that allows users to create workouts, add exercises to those workouts, and add/view comments related to each workout. It also enables users to track their progress and customize workout plans to suit their needs.
# Backend
- The backend is implemented in Java using the Spring Boot framework.
It manages all the business logic, user authentication, and CRUD operations for workouts, exercises, and comments.

# Core Features
- User Authentication:

- Secure user sign-up and log-in flows.
- JWT (JSON Web Token) is used for managing authentication and ensuring secure access to endpoints.
## Workout Management:

- Users can create workout sessions that consist of multiple exercises.
- Each workout session is with a specific date.
- Workouts can be updated, deleted, and reviewed.
## Exercise Management:

- Users can add exercises to workouts with specific attributes such as name, sets, and repetitions.
- Exercises can also be edited and deleted as needed.
## Comments on Workouts:

- Users can add custom comments or notes to individual workout sessions.
- This allows them to record feedback in the future.
## Schedule Management:

Workouts are tied to specific dates, allowing users to create a schedule.
Users can see active or pending workouts sorted by date.

## Data Modeling:

The backend uses Spring Data JPA for Object-Relational Mapping (ORM).
Key entities include Workout, Exercise, and Comment.

![image](https://github.com/user-attachments/assets/c8214485-c401-4a19-8ae8-6c58d4e089c4)

## API Endpoints

### Workout:
- GET /api/workouts – Retrieve all workouts.
- POST /api/workouts – Add a new workout.
- DELETE /api/workouts/{id} – Delete a workout.
- PUT /api/workouts/{id} – Edit a workout.
### Exercise:
- POST /api/exercises – Add a new exercise.
- PUT /api/exercises/{id} – Edit an exercise.
- DELETE /api/exercises/{id} – Delete an exercise.
### Comment:
- GET /api/comments/workout/{workoutId} – Get all comments for a specific workout.
- POST /api/comments/workout/{workoutId} – Add a comment to a specific workout.

# Frontend
- The frontend is implemented in React and offers an intuitive user interface for:

- Viewing and managing workout schedules.
- Adding and editing exercises and comments.
![image](https://github.com/user-attachments/assets/29f5d338-cddd-4741-b6c0-5961eb51e8c6)
![image](https://github.com/user-attachments/assets/5ea67c9f-cb9b-4ecc-8b32-891a1d77fd15)
![image](https://github.com/user-attachments/assets/84ad9193-9b22-4164-96a2-753b8e6f1c1e)
![image](https://github.com/user-attachments/assets/7c746682-f1e8-4c03-bbe3-f382af36380b)
![image](https://github.com/user-attachments/assets/f8fb279b-2fd4-4c5f-9402-8d77cdf15ce8)
![image](https://github.com/user-attachments/assets/f5ce951a-c119-4ce9-96cb-06a8ea5e6a52)
![image](https://github.com/user-attachments/assets/652ff04b-67ff-425b-98d6-3f96dabd785d)
![image](https://github.com/user-attachments/assets/55bb72a2-160e-4744-b06d-5337092fb782)
![image](https://github.com/user-attachments/assets/9d19b539-2ff2-45ff-b42b-9e9636c9eeaa)
# MySql
## Comment 
![image](https://github.com/user-attachments/assets/c6f96ce1-d66f-4533-8ae2-753c9f27bb4b)
## Exercise
![image](https://github.com/user-attachments/assets/cb5bcda2-78e7-4201-a85e-c19fd1b48196)
## User
![image](https://github.com/user-attachments/assets/6fe80894-c8bf-4e05-80cd-68aee870cfb4)
## Workout
![image](https://github.com/user-attachments/assets/5494fd24-6f58-4cfc-b24f-5b2f82da56b4)
















