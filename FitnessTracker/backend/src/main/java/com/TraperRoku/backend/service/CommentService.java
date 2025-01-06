package com.TraperRoku.backend.service;

import com.TraperRoku.backend.entities.Comment;
import com.TraperRoku.backend.entities.Workout;
import com.TraperRoku.backend.repository.CommentRepository;
import com.TraperRoku.backend.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

        @Autowired
        private CommentRepository commentRepository;

        @Autowired
        private WorkoutRepository workoutRepository;

        public Comment createCommentToWorkout(Long workoutId, String text){
            Workout workout =  workoutRepository.findById(workoutId).orElseThrow( () -> new RuntimeException("There is not that Workout Id"));

            Comment comment = new Comment();
            comment.setWorkout(workout);
            comment.setText(text);

            return commentRepository.save(comment);
        }

        public List<Comment> getAllCommentsByWorkoutId(Long workoutId){
            Workout workout =  workoutRepository.findById(workoutId).orElseThrow( () -> new RuntimeException("There is not that Workout Id"));

            return workout.getComments();
        }

        public void deleteComment(Long commentId) {

            commentRepository.deleteById(commentId);
        }

}
