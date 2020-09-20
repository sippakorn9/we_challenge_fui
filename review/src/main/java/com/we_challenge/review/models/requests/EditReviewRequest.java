package com.we_challenge.review.models.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EditReviewRequest {
    @NotNull(message = "event is required")
    private String reviewContent;

    public EditReviewRequest(String reviewContent) {
        this.reviewContent = reviewContent;
    }
}
