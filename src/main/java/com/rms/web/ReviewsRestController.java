package com.rms.web;

import com.rms.model.views.ReviewView;
import com.rms.service.Impl.ReviewServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewsRestController {

    private final ReviewServiceImpl reviewServiceImpl;

    public ReviewsRestController(ReviewServiceImpl reviewServiceImpl) {
        this.reviewServiceImpl = reviewServiceImpl;
    }

    @GetMapping
    public List<ReviewView> getAllReviews() {
        return reviewServiceImpl.getAllReviews();
    }
}
