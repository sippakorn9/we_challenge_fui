package com.we_challenge.review.services;

import com.we_challenge.review.entities.Keyword;
import com.we_challenge.review.entities.Review;
import com.we_challenge.review.models.requests.EditReviewRequest;
import com.we_challenge.review.models.responses.ReviewDetailResponse;
import com.we_challenge.review.models.responses.ReviewListResponse;
import com.we_challenge.review.repositories.KeywordRepository;
import com.we_challenge.review.repositories.ReviewRepository;
import com.we_challenge.review.repositories.ReviewRepositoryCustom;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private KeywordRepository keywordRepository;

    @Mock
    private ReviewRepositoryCustom reviewRepositoryCustom;

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

    @Test
    public void validateFoodKeywordShouldSuccessWhenNotFound() {
        Mockito.when(keywordRepository.findFirstByKeyword(Mockito.anyString())).thenReturn(null);
        Assert.assertFalse(reviewService.validateFoodKeyword("key word"));
    }

    @Test
    public void validateFoodKeywordShouldSuccessWhenFound() {
        Mockito.when(keywordRepository.findFirstByKeyword(Mockito.anyString())).thenReturn(new Keyword());
        Assert.assertTrue(reviewService.validateFoodKeyword("key word"));
    }

    @Test
    public void getReviewsByQueryShouldSuccess() {
        Review review1 = new Review(1, "eat fried rice, with another great fried rice");
        Review review2 = new Review(2, "with another great fried rice, eat Fried rice, eat fried rice");

        Mockito.when(reviewRepositoryCustom.searchReviewByQuery(Mockito.anyString())).thenReturn(Arrays.asList(review1, review2));
        ReviewListResponse response = reviewService.getReviewsByQuery("fried rice");
        Assert.assertEquals(2, response.getCount().intValue());
        Assert.assertTrue(response.getReviews().stream()
                .anyMatch(x -> x.getId().equals(1) && x.getReviewContent().equals("eat <keyword>fried rice</keyword>, with another great <keyword>fried rice</keyword>")));
        Assert.assertTrue(response.getReviews().stream()
                .anyMatch(x -> x.getId().equals(2) && x.getReviewContent().equals("with another great <keyword>fried rice</keyword>, eat <keyword>Fried rice</keyword>, eat <keyword>fried rice</keyword>")));

    }

}
