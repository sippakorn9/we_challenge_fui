package com.we_challenge.review.repositories;

import com.we_challenge.review.entities.Review;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql({"/data.sql"})
public class RepositoriesTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewRepositoryCustom reviewRepositoryCustom;

    @Test
    public void setUpSQLShouldSuccess() {
        List<Review> budgetUsagePerMonthList = reviewRepository.findAll();
        Assert.assertEquals(1000, budgetUsagePerMonthList.size());
    }

    @Test
    public void saveWithSkipVersionShouldThrowOptimisticLocking() {
        Exception actual = null;
        try {
            Review entity = new Review(1, "new content", 5);
            reviewRepository.save(entity);
        } catch (Exception e) {
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(ObjectOptimisticLockingFailureException.class, actual.getClass());
    }

    @Test
    public void searchReviewByQueryShouldSuccess() {
        Exception actual = null;
        List<Review> result = new ArrayList<>();
        try {
            result = reviewRepositoryCustom.searchReviewByQuery("กาแฟโบราณ");
        } catch (Exception e) {
            actual = e;
        }
        Assert.assertNull(actual);
        Assert.assertEquals(1, result.size());
    }

}
