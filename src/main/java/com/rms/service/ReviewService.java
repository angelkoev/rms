package com.rms.service;

import com.rms.model.dto.ReviewDTO;
import com.rms.model.entity.ReviewEntity;
import com.rms.model.entity.UserEntity;
import com.rms.model.views.ReviewView;
import com.rms.repositiry.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
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

//                    System.out.println();

                    String writtenBy = reviewEntity.getWrittenBy().getFirstName() + " " + reviewEntity.getWrittenBy().getLastName();
                    currentReview.setAuthor(writtenBy);

//                    String postedOnd = reviewEntity.getPostedOn().toString();
//                    currentReview.setDate(postedOnd);

                    return currentReview;

                })
                .collect(Collectors.toList());

        return reviews;
    }

    public void initReviews() {

        if (reviewRepository.count() != 0) {
            return;
        }

        Random random = new Random();

        int reviewCount = 20;
        while (reviewCount-- > 0) { //adding random reviews

            Long randomUserId = random.nextLong(userService.usersCount()) + 1;
            Optional<UserEntity> user = userService.findById(randomUserId);

            UserEntity userEntity = null;
            if (user.isPresent()) {
                userEntity = user.get();
            }

            if (userEntity != null) {
                ReviewEntity review = new ReviewEntity();
//                review.setPostedOn(LocalDate.now());
                review.setPostedOn(randomDate());
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

    private LocalDate randomDate() {
        int year = 2023;
        int month = ThreadLocalRandom.current().nextInt(1, 13);
        int day = ThreadLocalRandom.current().nextInt(1, LocalDate.of(year, month, 1).lengthOfMonth() + 1);
        LocalDate randomDate = LocalDate.of(year, month, day);

        return randomDate;
    }
}


