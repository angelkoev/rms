package com.rms.service;

import com.rms.model.dto.ReviewDTO;
import com.rms.model.views.ReviewView;

import java.util.List;

public interface ReviewService {
    void addReview (ReviewDTO reviewDTO);

    List<ReviewView> getAllReviews();
}
