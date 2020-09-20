package com.we_challenge.review.repositories;

import com.we_challenge.review.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
