package com.we_challenge.review.models.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditReviewRequest {
    private String reviewContent;

    public EditReviewRequest(String reviewContent) {
        this.reviewContent = reviewContent;
    }
}
