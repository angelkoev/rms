//package com.rms.service;
//
//import com.rms.model.entity.DrinkEntity;
//import com.rms.model.entity.DrinkTypeEnum;
//import com.rms.repository.DrinkRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import java.util.Optional;
//import java.util.Set;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class DrinkServiceTest {
//
//    @Mock
//    private DrinkRepository drinkRepository;
//
//    @InjectMocks
//    private DrinkService drinkService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testIsDrinkAlreadyAdded_ExistingDrink() {
//        // Arrange
//        DrinkEntity existingDrink = new DrinkEntity();
//        existingDrink.setId(1L);
//        existingDrink.setName("Coke");
//
//        when(drinkRepository.findByName("Coke")).thenReturn(Optional.of(existingDrink));
//
//        // Act
//        boolean result = drinkService.isDrinkAlreadyAdded(existingDrink);
//
//        // Assert
//        assertTrue(result);
//    }
//
//    @Test
//    void testIsDrinkAlreadyAdded_NonExistingDrink() {
//        // Arrange
//        when(drinkRepository.findByName("NonExistingDrink")).thenReturn(Optional.empty());
//
//        // Act
//        boolean result = drinkService.isDrinkAlreadyAdded(new DrinkEntity());
//
//        // Assert
//        assertFalse(result);
//    }
//
//    @Test
//    void testAddDrink_NewDrink() {
//        // Arrange
//        DrinkEntity newDrink = new DrinkEntity();
//        newDrink.setName("NewDrink");
//
//        when(drinkRepository.findByName("NewDrink")).thenReturn(Optional.empty());
//
//        // Act
//        assertDoesNotThrow(() -> drinkService.addDrink(newDrink));
//
//        // Assert
//        verify(drinkRepository, times(1)).save(newDrink);
//    }
//
//    @Test
//    void testAddDrink_ExistingDrink() {
//        // Arrange
//        DrinkEntity existingDrink = new DrinkEntity();
//        existingDrink.setName("ExistingDrink");
//
//        when(drinkRepository.findByName("ExistingDrink")).thenReturn(Optional.of(existingDrink));
//
//        // Act and Assert
//        assertThrows(IllegalStateException.class, () -> drinkService.addDrink(existingDrink));
//        verify(drinkRepository, never()).save(any());
//    }
//
//    @Test
//    void testFindAllBy() {
//        // Arrange
//        Set<DrinkEntity> drinkEntities = Set.of(new DrinkEntity(), new DrinkEntity());
//        when(drinkRepository.findAllBy()).thenReturn(drinkEntities);
//
//        // Act
//        Set<DrinkEntity> result = drinkService.findAllBy();
//
//        // Assert
//        assertEquals(drinkEntities, result);
//    }
//
//    @Test
//    void testFindAllByType() {
//        // Arrange
//        DrinkTypeEnum drinkType = DrinkTypeEnum.SODA;
//        Set<DrinkEntity> drinkEntities = Set.of(new DrinkEntity(), new DrinkEntity());
//        when(drinkRepository.findAllByTypeEquals(drinkType)).thenReturn(drinkEntities);
//
//        // Act
//        Set<DrinkEntity> result = drinkService.findAllByType(drinkType);
//
//        // Assert
//        assertEquals(drinkEntities, result);
//    }
//
//    @Test
//    void testFindById_ExistingId() {
//        // Arrange
//        Long existingId = 1L;
//        DrinkEntity existingDrink = new DrinkEntity();
//        existingDrink.setId(existingId);
//
//        when(drinkRepository.findById(existingId)).thenReturn(Optional.of(existingDrink));
//
//        // Act
//        DrinkEntity result = drinkService.findById(existingId);
//
//        // Assert
//        assertEquals(existingDrink, result);
//    }
//
//    @Test
//    void testFindById_NonExistingId() {
//        // Arrange
//        Long nonExistingId = 99L;
//
//        when(drinkRepository.findById(nonExistingId)).thenReturn(Optional.empty());
//
//        // Act
//        DrinkEntity result = drinkService.findById(nonExistingId);
//
//        // Assert
//        assertNull(result);
//    }
//
//}
