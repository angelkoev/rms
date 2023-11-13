package com.rms.service;

import com.rms.model.entity.ReviewEntity;
import com.rms.model.entity.UserEntity;
import com.rms.repositiry.ReviewRepository;
import jakarta.persistence.Column;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public ReviewService(ReviewRepository reviewRepository, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
    }

    public void initReviews() {

        if (reviewRepository.count() != 0) {
            return;
        }

        Random random = new Random();

        int reviewCount = 10;
        while (reviewCount-- > 0) { //adding random reviews

            Long randomUserId = random.nextLong(5) + 1;
            Optional<UserEntity> user = userService.findById(randomUserId);

            UserEntity userEntity = null;
            if (user.isPresent()) {
                userEntity = user.get();
            }

            if (userEntity != null) {
                ReviewEntity review = new ReviewEntity();
                review.setPostedOn(LocalDateTime.now());
                review.setContent(userEntity.getFirstName() + " " + userEntity.getLastName() + " " + userEntity.getEmail());
                int randomScore = random.nextInt(5) + 1; // score between 1 and 5
                review.setScore(randomScore);
                review.setWrittenBy(userEntity);
                reviewRepository.save(review);
            }

        }
        // Generate a random integer between 1 and 5

//        private UserEntity writtenBy;
//        private String content;


//        private LocalDateTime postedOn;

//        private int score;


    }
}
