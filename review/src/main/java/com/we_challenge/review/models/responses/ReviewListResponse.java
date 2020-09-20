package com.we_challenge.review.models.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReviewListResponse {
    private Integer count;
    private List<ReviewDetailResponse> reviews;
}
