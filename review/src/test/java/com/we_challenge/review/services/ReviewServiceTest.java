package com.we_challenge.review.services;

import com.we_challenge.review.entities.Review;
import com.we_challenge.review.models.requests.EditReviewRequest;
import com.we_challenge.review.models.responses.ReviewDetailResponse;
import com.we_challenge.review.repositories.ReviewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

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
        ReviewDetailResponse result = reviewService.getReviewDetailById(1);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.getId().intValue());
        Assert.assertEquals("review content with fried rice", result.getReviewContent());

        Mockito.verify(reviewRepository, Mockito.times(1)).findById(Mockito.eq(1));
    }

    @Test
    public void getReviewByIdShouldSuccessWhenNotFound() {
        Mockito.when(reviewRepository.findById(Mockito.eq(1))).thenReturn(Optional.empty());
        ReviewDetailResponse result = reviewService.getReviewDetailById(1);
        Assert.assertNull(result);

        Mockito.verify(reviewRepository, Mockito.times(1)).findById(Mockito.eq(1));
    }

    @Test
    public void saveReviewContentShouldSuccessWhenFound() {
        Mockito.when(reviewRepository.findById(Mockito.eq(1))).thenReturn(Optional.of(getMockReview()));
        reviewService.saveReviewContent(1, new EditReviewRequest("new content"));

        ArgumentCaptor<Review> reviewArgumentCaptor = ArgumentCaptor.forClass(Review.class);
        Mockito.verify(reviewRepository, Mockito.times(1)).save(reviewArgumentCaptor.capture());

        Review savedReview = reviewArgumentCaptor.getValue();
        Assert.assertEquals(1, savedReview.getId().intValue());
        Assert.assertEquals("new content", savedReview.getContent());
    }

    @Test
    public void saveReviewContentShouldSuccessWhenNotFound() {
        Mockito.when(reviewRepository.findById(Mockito.eq(1))).thenReturn(Optional.empty());
        reviewService.saveReviewContent(1, new EditReviewRequest("new content"));

        ArgumentCaptor<Review> reviewArgumentCaptor = ArgumentCaptor.forClass(Review.class);
        Mockito.verify(reviewRepository, Mockito.times(1)).save(reviewArgumentCaptor.capture());

        Review savedReview = reviewArgumentCaptor.getValue();
        Assert.assertEquals(1, savedReview.getId().intValue());
        Assert.assertEquals("new content", savedReview.getContent());
    }

}
