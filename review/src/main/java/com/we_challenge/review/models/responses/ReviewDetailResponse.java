package com.we_challenge.review.models.responses;

import com.we_challenge.review.entities.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDetailResponse {
    private Integer id;
    private String reviewContent;

    public ReviewDetailResponse(Review review) {
        this.id = review.getId();
        this.reviewContent = review.getContent();
    }
}
