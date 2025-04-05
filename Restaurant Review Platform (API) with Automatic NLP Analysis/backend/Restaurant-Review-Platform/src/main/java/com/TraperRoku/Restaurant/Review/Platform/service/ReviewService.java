package com.TraperRoku.Restaurant.Review.Platform.service;

import com.TraperRoku.Restaurant.Review.Platform.dto.ReviewDto;
import com.TraperRoku.Restaurant.Review.Platform.entity.Review;
import com.TraperRoku.Restaurant.Review.Platform.exception.AppException;
import com.TraperRoku.Restaurant.Review.Platform.repository.ReviewRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final NlpService nlpService;

    public ReviewService(ReviewRepository reviewRepository, NlpService nlpService) {
        this.reviewRepository = reviewRepository;
        this.nlpService = nlpService;
    }

    public Review addReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setText(reviewDto.getText());
        review.setSentimentScore(nlpService.analyzeSentiment(reviewDto.getText()));
        return reviewRepository.save(review);
    }

    public void updateReview(ObjectId reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new AppException("Review not found with ID" + reviewId, HttpStatus.NOT_FOUND));
    }

    public void deleteReview(ObjectId reviewId){
        reviewRepository.deleteById(reviewId);
    }

    public List<Review> findAllReview (){
        return reviewRepository.findAll();
    }


}
