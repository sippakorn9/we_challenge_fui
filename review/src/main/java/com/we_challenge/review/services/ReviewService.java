package com.we_challenge.review.services;

import com.we_challenge.review.entities.Review;
import com.we_challenge.review.models.requests.EditReviewRequest;
import com.we_challenge.review.models.responses.ReviewDetailResponse;
import com.we_challenge.review.repositories.ReviewRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    private Review findReviewById(Integer id) {
        Optional<Review> reviewOp = reviewRepository.findById(id);
        return reviewOp.orElse(null);
    }

    public ReviewDetailResponse getReviewDetailById(Integer id) {
        Review review = findReviewById(id);
        return review == null ? null : new ReviewDetailResponse(review);
    }


    public void saveReviewContent(Integer id, EditReviewRequest editReviewRequest) {
        Review review = findReviewById(id);
        if (review == null) {
            review = new Review(id);
        }
        review.setContent(editReviewRequest.getReviewContent());

        saveReview(review);
    }

    @Retryable(value = {
            ObjectOptimisticLockingFailureException.class
    },
            maxAttempts = Integer.MAX_VALUE,
            backoff = @Backoff(delay = 500)
    )
    private void saveReview(Review review) {
        reviewRepository.save(review);
    }


}
