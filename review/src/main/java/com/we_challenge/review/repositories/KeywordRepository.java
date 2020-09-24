package com.we_challenge.review.repositories;

import com.we_challenge.review.entities.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {
    Keyword findFirstByKeyword(String keyword);
}
