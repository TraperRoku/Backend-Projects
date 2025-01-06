import React, { useState, useEffect } from 'react';
import { request } from '../../axios_helper';

const AddComment = ({ workoutId }) => {
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');

  // Fetch comments when the component mounts or the workoutId changes
  useEffect(() => {
    fetchComments();
  }, [workoutId]);

  const fetchComments = () => {
    request('GET', `/api/comments/workout/${workoutId}`)
      .then((response) => {
        setComments(response.data);  
      })
      .catch((error) => console.error('Error fetching comments:', error));
  };

  const handleAddComment = () => {
    if (!newComment.trim()) return;  // Avoid adding empty comments

    request('POST', `/api/comments/workout/${workoutId}`, { text: newComment })
      .then((response) => {
        setComments((prevComments) => [...prevComments, response.data]);  // Add the new comment to the list
        setNewComment('');  // Reset input after comment is added
      })
      .catch((error) => console.error('Error adding comment:', error));
  };

  const handleDeleteComment = (commentId) => {
    request('DELETE', `/api/comments/workout/${commentId}`)
      .then(() => {
        // Remove the deleted comment from the list
        setComments(comments.filter((comment) => comment.commentId !== commentId));
      })
      .catch((error) => console.error('Error deleting comment:', error));
  };

  return (
    <div>
      <h4>Comments:</h4>
      <ul>
        {comments.length > 0 ? (
          comments.map((comment) => (
            <li key={comment.commentId}>
              {comment.text}  {/* Display the comment text */}
              <button onClick={() => handleDeleteComment(comment.commentId)}>Delete</button>  {/* Delete button */}
            </li>
          ))
        ) : (
          <li>No comments yet</li>
        )}
      </ul>

      <div>
        <input
          type="text"
          placeholder="Add a comment..."
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
        />
        <button onClick={handleAddComment}>Add Comment</button>
      </div>
    </div>
  );
};

export default AddComment;
