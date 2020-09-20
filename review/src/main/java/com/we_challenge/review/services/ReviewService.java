package com.we_challenge.review.services;

import com.we_challenge.review.entities.Review;
import com.we_challenge.review.models.responses.ReviewDetailResponse;
import com.we_challenge.review.repositories.ReviewRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ReviewDetailResponse getReviewById(Integer id) {
        Optional<Review> reviewOp = reviewRepository.findById(id);
        return reviewOp.map(ReviewDetailResponse::new).orElse(null);
    }

}
