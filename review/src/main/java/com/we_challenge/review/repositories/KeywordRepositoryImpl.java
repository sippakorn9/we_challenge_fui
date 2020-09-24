package com.we_challenge.review.repositories;

import com.we_challenge.review.entities.Keyword;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class KeywordRepositoryImpl implements KeywordRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Keyword searchKeyword(String keyword) {
        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(em);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Keyword.class)
                .get();

        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .wildcard()
                .onField("keyword")
                .matching(keyword)
                .createQuery();

        org.hibernate.search.jpa.FullTextQuery fullTextQuery
                = fullTextEntityManager.createFullTextQuery(query, Keyword.class);
        try {
            List results = fullTextQuery.getResultList();
            if (results.size() == 0) {
                return null;
            }
            return (Keyword) results.get(0);
        } catch (NoResultException e) {
            return null;
        }
    }
}
