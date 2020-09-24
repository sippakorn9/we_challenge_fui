package com.we_challenge.review.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.we_challenge.review.models.requests.EditReviewRequest;
import com.we_challenge.review.models.responses.ReviewDetailResponse;
import com.we_challenge.review.models.responses.ReviewListResponse;
import com.we_challenge.review.services.ReviewService;
import com.we_challenge.review.utils.Protocol;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                .build();
    }

    @Test
    public void getReviewByIdShouldErrorWhenNotFound() throws Exception {
        String url = Protocol.REVIEW_BY_ID.replace("{id}", "1");

        Mockito.when(reviewService.getReviewDetailById(Mockito.any())).thenReturn(null);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(url);

        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("review not found."));
    }


    @Test
    public void getReviewByIdShouldSuccess() throws Exception {
        String url = Protocol.REVIEW_BY_ID.replace("{id}", "1");
        ReviewDetailResponse response = new ReviewDetailResponse(1, "content");

        Mockito.when(reviewService.getReviewDetailById(Mockito.any())).thenReturn(response);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(url);

        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reviewContent").value("content"));
    }

    @Test
    public void editReviewByIdShouldErrorWhenNotNullContent() throws Exception {
        String url = Protocol.REVIEW_BY_ID.replace("{id}", "1");

        EditReviewRequest editReviewRequest = new EditReviewRequest();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(editReviewRequest))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("reviewContent is required."));
    }

    @Test
    public void editReviewByIdShouldSuccess() throws Exception {
        String url = Protocol.REVIEW_BY_ID.replace("{id}", "1");
        EditReviewRequest editReviewRequest = new EditReviewRequest("new content");

        Mockito.doNothing().when(reviewService).saveReviewContent(Mockito.eq(1), Mockito.any());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(editReviewRequest))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getReviewByQueryShouldSuccessWhenNotExistKeyword() throws Exception {
        String url = Protocol.REVIEWS + "?query=not fried rice";

        Mockito.when(reviewService.validateFoodKeyword(Mockito.anyString())).thenReturn(false);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(url);

        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(0));
        Mockito.verify(reviewService, Mockito.never()).getReviewsByQuery(Mockito.anyString());
    }

    @Test
    public void getReviewByQueryShouldSuccessWhenExistKeyword() throws Exception {
        String url = Protocol.REVIEWS + "?query=fried rice";
        ReviewListResponse response = new ReviewListResponse();
        response.setCount(1);

        Mockito.when(reviewService.validateFoodKeyword(Mockito.anyString())).thenReturn(true);
        Mockito.when(reviewService.getReviewsByQuery(Mockito.anyString())).thenReturn(response);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(url);

        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(1));
        Mockito.verify(reviewService, Mockito.times(1)).getReviewsByQuery(Mockito.anyString());
    }

}
