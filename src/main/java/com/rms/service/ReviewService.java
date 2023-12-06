package com.rms.service;

import com.rms.model.dto.ReviewDTO;
import com.rms.model.entity.ReviewEntity;
import com.rms.model.entity.UserEntity;
import com.rms.model.views.ReviewView;
import com.rms.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ReviewService(ReviewRepository reviewRepository, UserService userService, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public void addReview (ReviewDTO reviewDTO) {

        ReviewEntity review = modelMapper.map(reviewDTO, ReviewEntity.class);

        UserEntity user = userService.findUserEntityByUsername(reviewDTO.getUsername());
        review.setWrittenBy(user);
        review.setPostedOn(LocalDate.now());

        reviewRepository.save(review);
    }

    public List<ReviewView> getAllReviews() {

        List<ReviewEntity> reviewEntities = reviewRepository.findAllByOrderByPostedOnDesc();

        List<ReviewView> reviews = reviewEntities.stream()
                .map(reviewEntity -> {
                    ReviewView currentReview = modelMapper.map(reviewEntity, ReviewView.class);

                    String writtenBy = reviewEntity.getWrittenBy().getFirstName();
                    currentReview.setAuthor(writtenBy);

                    return currentReview;

                })
                .collect(Collectors.toList());

        return reviews;
    }

}


