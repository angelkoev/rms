package com.rms.service;

import com.rms.model.dto.ReviewDTO;
import com.rms.model.entity.ReviewEntity;
import com.rms.model.entity.UserEntity;
import com.rms.model.views.ReviewView;
import com.rms.repository.ReviewRepository;
import com.rms.service.Impl.ReviewServiceImpl;
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

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserService userService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testAddReview_ValidDTO() {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setScore("4");
        reviewDTO.setContent("valid test content");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("valid_test_username");
        reviewDTO.setUsername(userEntity.getUsername());

        when(userService.findUserEntityByUsername(any())).thenReturn(userEntity);
        ReviewEntity reviewEntity = new ReviewEntity();
        when(modelMapper.map(any(), eq(ReviewEntity.class))).thenReturn(reviewEntity);
        reviewEntity.setWrittenBy(userEntity);

        // Act
        reviewServiceImpl.addReview(reviewDTO);

        // Assert
        verify(reviewRepository, times(1)).save(any());
    }

    @Test
    public void testAddReview_InvalidDTO() {
        // Arrange
        ReviewDTO invalidReviewDTO = new ReviewDTO();
        invalidReviewDTO.setScore("6");
        invalidReviewDTO.setContent("test_content");
        invalidReviewDTO.setUsername("valid_test_username");

        // Act
        Set<ConstraintViolation<ReviewDTO>> violations = validator.validate(invalidReviewDTO);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testGetAllReviews() {
        // Arrange
        ReviewEntity reviewEntity = new ReviewEntity();
        UserEntity userEntity = new UserEntity();
        reviewEntity.setWrittenBy(userEntity);
        when(reviewRepository.findAllByOrderByPostedOnDesc()).thenReturn(List.of(reviewEntity));

        ReviewView reviewView = new ReviewView();
        when(modelMapper.map(any(), eq(ReviewView.class))).thenReturn(reviewView);

        // Act
        List<ReviewView> result = reviewServiceImpl.getAllReviews();

        // Assert
        assertEquals(1, result.size());
        assertEquals(reviewView, result.get(0));
    }

}
