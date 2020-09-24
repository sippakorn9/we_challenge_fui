package com.we_challenge.review.models.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReviewListResponse {
    private Integer count = 0;
    private List<ReviewDetailResponse> reviews = new ArrayList<>();
}
