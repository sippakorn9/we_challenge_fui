package com.we_challenge.review.repositories;

import com.we_challenge.review.entities.Review;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Review> searchReviewByQuery(String keyword) {
        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(em);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Review.class)
                .get();

        org.apache.lucene.search.Query query = queryBuilder
                .simpleQueryString()
                .onField("content")
                .matching("\"" + keyword + "\"")
                .createQuery();

        org.hibernate.search.jpa.FullTextQuery fullTextQuery
                = fullTextEntityManager.createFullTextQuery(query, Review.class);
        List<Review> result = fullTextQuery.getResultList();
        return result;
    }
}
