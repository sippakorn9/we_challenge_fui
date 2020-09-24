package com.we_challenge.review.services;

import com.we_challenge.review.entities.Review;
import com.we_challenge.review.models.requests.EditReviewRequest;
import com.we_challenge.review.models.responses.ReviewDetailResponse;
import com.we_challenge.review.models.responses.ReviewListResponse;
import com.we_challenge.review.repositories.KeywordRepositoryCustom;
import com.we_challenge.review.repositories.ReviewRepository;
import com.we_challenge.review.repositories.ReviewRepositoryCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final ReviewRepository reviewRepository;

    private final KeywordRepositoryCustom keywordRepositoryCustom;

    private final ReviewRepositoryCustom reviewRepositoryCustom;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         KeywordRepositoryCustom keywordRepositoryCustom,
                         ReviewRepositoryCustom reviewRepositoryCustom) {
        this.reviewRepository = reviewRepository;
        this.keywordRepositoryCustom = keywordRepositoryCustom;
        this.reviewRepositoryCustom = reviewRepositoryCustom;
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

    public boolean validateFoodKeyword(String keyword) {
        return keywordRepositoryCustom.searchKeyword(keyword) != null;
    }

    public ReviewListResponse getReviewsByQuery(String keyword) {
        ReviewListResponse response = new ReviewListResponse();
        List<Review> reviewList = reviewRepositoryCustom.searchReviewByQuery(keyword);
        reviewList.parallelStream().forEach(
                x -> {
                    String markedContent = x.getContent().replaceAll("(?i)" + keyword + "(?-i)", "<keyword>$0</keyword>");
                    x.setContent(markedContent);
                }
        );
        response.setCount(reviewList.size());
        logger.info("[getReviewsByQuery] found reviews by query {} size : {}", keyword, response.getCount());
        response.setReviews(reviewList.stream().map(ReviewDetailResponse::new).collect(Collectors.toList()));
        return response;
    }


}
