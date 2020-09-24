package com.we_challenge.review.repositories;

import com.we_challenge.review.entities.Review;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<Review> searchReviewByQuery(String keyword);
}
