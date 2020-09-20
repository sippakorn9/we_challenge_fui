package com.we_challenge.review.services;

import com.we_challenge.review.entities.Review;
import com.we_challenge.review.models.responses.ReviewDetailResponse;
import com.we_challenge.review.repositories.ReviewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {
    @InjectMocks
    ReviewService reviewService;

    @Mock
    ReviewRepository reviewRepository;

    private Review getMockReview() {
        return new Review(1, "review content with fried rice", 0);
    }

    @Test
    public void getReviewByIdShouldSuccessWhenFound() {
        Mockito.when(reviewRepository.findById(Mockito.eq(1))).thenReturn(Optional.of(getMockReview()));
        ReviewDetailResponse result = reviewService.getReviewById(1);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.getId().intValue());
        Assert.assertEquals("review content with fried rice", result.getReviewContent());

        Mockito.verify(reviewRepository, Mockito.times(1)).findById(Mockito.eq(1));
    }

    @Test
    public void getReviewByIdShouldSuccessWhenNotFound() {
        Mockito.when(reviewRepository.findById(Mockito.eq(1))).thenReturn(Optional.empty());
        ReviewDetailResponse result = reviewService.getReviewById(1);
        Assert.assertNull(result);

        Mockito.verify(reviewRepository, Mockito.times(1)).findById(Mockito.eq(1));
    }
}
