package com.rms.web;

import com.rms.model.views.ReviewView;
import com.rms.service.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewsRestController {

    private final ReviewService reviewService;

    public ReviewsRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewView> getAllReviews() {
        return reviewService.getAllReviews();
    }
}
