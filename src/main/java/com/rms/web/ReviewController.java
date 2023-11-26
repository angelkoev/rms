package com.rms.web;

import com.rms.model.views.ReviewView;
import com.rms.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/all")
    public String viewAll() {
        return "allReviews";
    }

    @GetMapping("/add")
    public String showAddReviewForm() {
        return "addReviewForm";
    }
}
