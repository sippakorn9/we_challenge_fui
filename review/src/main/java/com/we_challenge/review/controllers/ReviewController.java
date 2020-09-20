package com.we_challenge.review.controllers;

import com.we_challenge.review.models.responses.ErrorResponse;
import com.we_challenge.review.models.responses.ReviewDetailResponse;
import com.we_challenge.review.services.ReviewService;
import com.we_challenge.review.utils.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(Protocol.REVIEW_BY_ID)
    public ResponseEntity<?> getReviewById(@PathVariable("id") Integer id) {
        logger.info("[Request to getReviewById : {}]", id);
        ReviewDetailResponse response = reviewService.getReviewById(id);
        if (response == null) {
            logger.error("[ERROR] Review not found");
            return new ResponseEntity<>(new ErrorResponse("404", "review not found."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
