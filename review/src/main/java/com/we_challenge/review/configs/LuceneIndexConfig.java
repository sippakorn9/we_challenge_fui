package com.we_challenge.review.configs;

import com.we_challenge.review.services.LuceneIndexService;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;

public class LuceneIndexConfig {

    @Bean
    public LuceneIndexService luceneIndexServiceBean(EntityManagerFactory EntityManagerFactory) {
        LuceneIndexService luceneIndexServiceBean = new LuceneIndexService(EntityManagerFactory);
        luceneIndexServiceBean.triggerIndexing();
        return luceneIndexServiceBean;
    }
}
