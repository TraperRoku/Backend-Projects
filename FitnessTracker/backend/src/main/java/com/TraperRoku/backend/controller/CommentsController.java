package com.TraperRoku.backend.controller;

import com.TraperRoku.backend.Dto.CommentDto;
import com.TraperRoku.backend.entities.Comment;
import com.TraperRoku.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/comments")
@RestController
public class CommentsController {


    @Autowired
    public CommentService commentService;

    @GetMapping("/workout/{workoutId}")
    public List<Comment> getCommentFromWorkoutId(@PathVariable Long workoutId){
        // Return the list of Comment objects (not just the text)
        return commentService.getAllCommentsByWorkoutId(workoutId);
    }

    @PostMapping("/workout/{workoutId}")
    public Comment createComment(@PathVariable Long workoutId, @RequestBody CommentDto commentDto){
       return commentService.createCommentToWorkout(workoutId,commentDto.getText());
    }

    @DeleteMapping("/workout/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }



}
