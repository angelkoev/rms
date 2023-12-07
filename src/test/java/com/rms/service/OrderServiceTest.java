package com.rms.service;

import com.rms.events.DrinksCacheEvictEvent;
import com.rms.events.FoodsCacheEvictEvent;
import com.rms.model.dto.DrinkDTO;
import com.rms.model.dto.FoodDTO;
import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.OrderEntity;
import com.rms.model.entity.UserEntity;
import com.rms.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepositoryMock;

    @Mock
    private FoodService foodServiceMock;

    @Mock
    private DrinkService drinkServiceMock;

    @Mock
    private ModelMapper modelMapperMock;

    @Mock
    private ApplicationEventPublisher eventPublisherMock;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddDrink() {
        // Arrange
        DrinkDTO drinkDTO = new DrinkDTO();
        DrinkEntity drinkEntity = new DrinkEntity();
        when(modelMapperMock.map((drinkDTO), (DrinkEntity.class))).thenReturn(drinkEntity);

        // Act
        orderService.addDrink(drinkDTO, false);

        // Assert
        verify(drinkServiceMock, times(1)).addDrink(drinkEntity);

    }

    @Test
    public void testAddFood() {
        FoodDTO foodDTO = new FoodDTO();
        FoodEntity foodEntity = new FoodEntity();
        when(modelMapperMock.map((foodDTO), (FoodEntity.class))).thenReturn(foodEntity);

        orderService.addFood(foodDTO, false);

        verify(foodServiceMock, times(1)).addFood(foodEntity);
    }

    @Test
    public void testClearDrinksCache() {
        // Arrange

        // Act
        orderService.clearDrinksCache();

        // Assert
        verify(eventPublisherMock, times(1)).publishEvent(any(DrinksCacheEvictEvent.class));
    }

    @Test
    public void testClearFoodsCache() {
        // Arrange

        // Act
        orderService.clearFoodsCache();

        // Assert
        verify(eventPublisherMock, times(1)).publishEvent(any(FoodsCacheEvictEvent.class));
    }

    @Test
    public void testAddToMenuWithDrink() {
        // Arrange
        DrinkEntity drinkEntity = new DrinkEntity();
        OrderEntity menu = new OrderEntity();
        when(orderRepositoryMock.findById(1L)).thenReturn(Optional.of(menu));

        // Act
        orderService.addToMenu(drinkEntity);

        // Assert
        // Add your assertions here
        verify(orderRepositoryMock, times(1)).save(menu);
        // Add more verifications if needed
    }

    @Test
    public void testAddToMenuWithFood() {
        // Arrange
        FoodEntity foodEntity = new FoodEntity();
        OrderEntity menu = new OrderEntity();

        when(orderRepositoryMock.findById(1L)).thenReturn(Optional.of(menu));

        // Act
        orderService.addToMenu(foodEntity);

        // Assert
        // Add your assertions here
        verify(orderRepositoryMock, times(1)).save(menu);
        // Add more verifications if needed
    }

    @Test
    public void testCreateNewLastOrder() {
        // Arrange
        UserEntity userEntity = new UserEntity();

        // Act
        OrderEntity lastOrder = orderService.createNewLastOrder(userEntity);

        // Assert
        assertNotNull(lastOrder);
        assertEquals(userEntity, lastOrder.getMadeBy());
    }

    @Test
    public void testIsMenuOKWhenMenuExists() {
        // Arrange
        OrderEntity menu = new OrderEntity();
        orderRepositoryMock.save(menu);
        when(orderRepositoryMock.findById(1L)).thenReturn(Optional.of(menu));

        // Act
        boolean result = orderService.isMenuOK();

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsMenuOKWhenMenuDoesNotExist() {
        // Arrange
        when(orderRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // Act
        boolean result = orderService.isMenuOK();

        // Assert
        assertFalse(result);
    }
}
