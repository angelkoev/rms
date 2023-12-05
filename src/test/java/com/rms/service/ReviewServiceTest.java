package com.rms.service;

import com.rms.model.dto.ReviewDTO;
import com.rms.model.entity.ReviewEntity;
import com.rms.model.entity.UserEntity;
import com.rms.model.views.ReviewView;
import com.rms.repositiry.ReviewRepository;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReviewService reviewService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testAddReview_ValidDTO() {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO(/* Initialize with valid test data */);
        reviewDTO.setScore("4");
        reviewDTO.setContent("ала бала");

        UserEntity userEntity = new UserEntity(); // Initialize with test data
        userEntity.setUsername("pesho");
        reviewDTO.setUsername(userEntity.getUsername());

        when(userService.findUserEntityByUsername(any())).thenReturn(userEntity);
        ReviewEntity reviewEntity = new ReviewEntity();
        when(modelMapper.map(any(), eq(ReviewEntity.class))).thenReturn(reviewEntity);
        reviewEntity.setWrittenBy(userEntity);

        // Act
        reviewService.addReview(reviewDTO);

        // Assert
        verify(reviewRepository, times(1)).save(any());
    }

    @Test
    void testAddReview_InvalidDTO() {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO(/* Initialize with invalid test data */);

        // Act
        Set<ConstraintViolation<ReviewDTO>> violations = validator.validate(reviewDTO);

        // Assert
        assertFalse(violations.isEmpty());
        // Add more assertions based on your validation annotations
    }

    @Test
    void testGetAllReviews() {
        // Arrange
        ReviewEntity reviewEntity = new ReviewEntity(); // Initialize with test data
        UserEntity userEntity = new UserEntity();
        String firstName = "Pesho";
        reviewEntity.setWrittenBy(userEntity);
        when(reviewRepository.findAllByOrderByPostedOnDesc()).thenReturn(Arrays.asList(reviewEntity));

        ReviewView reviewView = new ReviewView(); // Initialize with test data
        when(modelMapper.map(any(), eq(ReviewView.class))).thenReturn(reviewView);

        // Act
        List<ReviewView> result = reviewService.getAllReviews();

        // Assert
        assertEquals(1, result.size());
        assertEquals(reviewView, result.get(0));
    }

    // Helper method to create a valid ReviewDTO
    private ReviewDTO createValidReviewDTO() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setContent("Valid review content");
        reviewDTO.setScore("5");
        reviewDTO.setUsername("validUsername");
        return reviewDTO;
    }

    // Helper method to create an invalid ReviewDTO
    private ReviewDTO createInvalidReviewDTO() {
        ReviewDTO reviewDTO = new ReviewDTO();
        // Intentionally leaving content empty to violate @Size constraint
        reviewDTO.setScore("6"); // Violating @Max constraint
        // Intentionally leaving username blank to violate @NotBlank constraint
        return reviewDTO;
    }
}
