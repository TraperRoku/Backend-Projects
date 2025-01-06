    package com.TraperRoku.backend.controller;


    import com.TraperRoku.backend.Dto.UserDto;
    import com.TraperRoku.backend.entities.User;
    import com.TraperRoku.backend.entities.Workout;
    import com.TraperRoku.backend.service.JwtService;
    import com.TraperRoku.backend.service.UserService;
    import com.TraperRoku.backend.service.WorkoutService;
    import jakarta.servlet.http.HttpServletRequest;
    import org.hibernate.jdbc.Work;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.web.bind.annotation.*;

    import java.security.Principal;
    import java.time.LocalDate;
    import java.util.List;
    import java.util.stream.Collectors;

    @RestController
    @RequestMapping("/api/workouts")
    public class WorkoutController {

        @Autowired
        public WorkoutService workoutService;

        @Autowired
        public UserService userService;

        @Autowired
        private JwtService jwtService;

        @GetMapping
        public List<Workout> getAllWorkouts(HttpServletRequest request) {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String login = jwtService.extractLogin(token);
                User user = userService.findByLogin1(login);

                return workoutService.getWorkoutsByUser(user);
            } else {
                throw new RuntimeException("Authorization header is missing or invalid.");
            }
        }
        @GetMapping("/week/{start}")
        public List<Workout> getWorkoutsForWeek(@PathVariable String start) {
            LocalDate weekStart = LocalDate.parse(start);
            return workoutService.getWorkoutsForWeek(weekStart);
        }
        @GetMapping("/workout")
        public List<Workout> getWorkouts(@RequestParam("weekStart") String weekStart) {
            LocalDate startDate = LocalDate.parse(weekStart);
            LocalDate endDate = startDate.plusDays(6);


            return workoutService.getAllWorkouts().stream()
                    .filter(workout -> !workout.getDate().isBefore(startDate) && !workout.getDate().isAfter(endDate))
                    .collect(Collectors.toList());
        }

        @GetMapping("/{id}")
        public Workout getWorkoutById(@PathVariable Long id){
            return workoutService.getWorkoutById(id);
        }



        @PostMapping
        public Workout createWorkout(@RequestBody Workout workout, HttpServletRequest request) {

            String authorizationHeader = request.getHeader("Authorization");


            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

                String token = authorizationHeader.substring(7);
                System.out.println("Received Token: " + token);  // Debug line
                String login = jwtService.extractLogin(token);

                User user = userService.findByLogin1(login);
                workout.setUser(user);

                return workoutService.saveWorkout(workout);
            } else {
                throw new RuntimeException("Authorization header is missing or invalid.");
            }
        }

        @PutMapping("{id}")
        public Workout updateWorkoutById(@PathVariable Long id, @RequestBody Workout UpdatedWorkout){
            Workout workoutById = workoutService.getWorkoutById(id);

            if(workoutById == null){
                throw new RuntimeException("There is not that workoutId "+ id);
            }

            workoutById.setExercises(UpdatedWorkout.getExercises());
            workoutById.setNameWorkout(UpdatedWorkout.getNameWorkout());

            workoutById.setComments(UpdatedWorkout.getComments());

            return workoutService.saveWorkout(workoutById);
        }

        @DeleteMapping("{id}")
        public void deleteWorkoutById(@PathVariable Long id){
            workoutService.deleteWorkout(id);
        }
    }
