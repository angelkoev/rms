package com.rms.service;

import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.FoodTypeEnum;
import com.rms.repository.FoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FoodServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private FoodService foodService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsFoodAlreadyAdded_ExistingFood() {
        // Arrange
        FoodEntity existingFood = new FoodEntity();
        existingFood.setId(1L);
        existingFood.setName("Pizza");

        when(foodRepository.findByName("Pizza")).thenReturn(Optional.of(existingFood));

        // Act
        boolean result = foodService.isFoodAlreadyAdded(existingFood);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsFoodAlreadyAdded_NonExistingFood() {
        // Arrange
        when(foodRepository.findByName("NonExistingFood")).thenReturn(Optional.empty());

        // Act
        boolean result = foodService.isFoodAlreadyAdded(new FoodEntity());

        // Assert
        assertFalse(result);
    }

    @Test
    void testAddFood_NewFood() {
        // Arrange
        FoodEntity newFood = new FoodEntity();
        newFood.setName("NewFood");

        when(foodRepository.findByName("NewFood")).thenReturn(Optional.empty());

        // Act
        assertDoesNotThrow(() -> foodService.addFood(newFood));

        // Assert
        verify(foodRepository, times(1)).save(newFood);
    }

    @Test
    void testAddFood_ExistingFood() {
        // Arrange
        FoodEntity existingFood = new FoodEntity();
        existingFood.setName("ExistingFood");

        when(foodRepository.findByName("ExistingFood")).thenReturn(Optional.of(existingFood));

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> foodService.addFood(existingFood));

        // Verify that save was not called
        verify(foodRepository, never()).save(any());
    }

    @Test
    void testFindAllBy() {
        // Arrange
        Set<FoodEntity> foodEntities = Set.of(new FoodEntity(), new FoodEntity());
        when(foodRepository.findAllBy()).thenReturn(foodEntities);

        // Act
        Set<FoodEntity> result = foodService.findAllBy();

        // Assert
        assertEquals(foodEntities, result);
    }

    @Test
    void testFindAllByType() {
        // Arrange
        FoodTypeEnum foodType = FoodTypeEnum.PIZZA;
        Set<FoodEntity> foodEntities = Set.of(new FoodEntity(), new FoodEntity());
        when(foodRepository.findAllByTypeEquals(foodType)).thenReturn(foodEntities);

        // Act
        Set<FoodEntity> result = foodService.findAllByType(foodType);

        // Assert
        assertEquals(foodEntities, result);
    }

    @Test
    void testFindById_ExistingId() {
        // Arrange
        Long existingId = 1L;
        FoodEntity existingFood = new FoodEntity();
        existingFood.setId(existingId);

        when(foodRepository.findById(existingId)).thenReturn(Optional.of(existingFood));

        // Act
        FoodEntity result = foodService.findById(existingId);

        // Assert
        assertEquals(existingFood, result);
    }

    @Test
    void testFindById_NonExistingId() {
        // Arrange
        Long nonExistingId = 99L;

        when(foodRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act
        FoodEntity result = foodService.findById(nonExistingId);

        // Assert
        assertNull(result);
    }

    // Additional tests can be added based on specific scenarios and edge cases.
}
