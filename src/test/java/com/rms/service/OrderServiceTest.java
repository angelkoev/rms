package com.rms.service;

import com.rms.events.DrinksCacheEvictEvent;
import com.rms.events.FoodsCacheEvictEvent;
import com.rms.model.dto.DrinkDTO;
import com.rms.model.dto.FoodDTO;
import com.rms.model.entity.*;
import com.rms.model.views.DrinkView;
import com.rms.repository.OrderRepository;
import com.rms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @Mock
    private UserRepository userRepositoryMock;
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

    @Test
    public void testMakeNewOrder() {
        // Arrange
        UserEntity currentUser = createFakeUserWithLastOrder();

        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(currentUser));
        when(orderRepositoryMock.save(any(OrderEntity.class))).thenAnswer(invocation -> {
            OrderEntity savedOrder = invocation.getArgument(0);
            savedOrder.setId(1L);
            return savedOrder;
        });

        // Act
        OrderEntity result = orderService.makeNewOrder(currentUser);

        // Assert
        assertNotNull(result);
        when(userRepositoryMock.findById(eq(currentUser.getId()))).thenReturn(Optional.of(currentUser));
        verify(orderRepositoryMock, times(1)).save(any(OrderEntity.class));
    }

    private UserEntity createFakeUserWithLastOrder() {
        UserEntity user = new UserEntity();
        user.setLastOrder(new OrderEntity());
        user.setId(1L);
        return user;
    }

    public static List<OrderEntity> createFakeOrders() {
        List<OrderEntity> fakeOrders = new ArrayList<>();

        UserEntity user = createUser("testUser");

        DrinkEntity drink1 = createDrink("Cola", BigDecimal.valueOf(2.5), 500);
        DrinkEntity drink2 = createDrink("Orange Juice", BigDecimal.valueOf(3.0), 400);
        FoodEntity food1 = createFood("Burger", BigDecimal.valueOf(5.0));
        FoodEntity food2 = createFood("Pizza", BigDecimal.valueOf(8.0));

        OrderEntity order1 = createOrder(user, LocalDateTime.now().minusDays(1), false, false, List.of(drink1), List.of(food1));
        OrderEntity order2 = createOrder(user, LocalDateTime.now().minusDays(2), true, true, List.of(drink2), List.of(food2));

        fakeOrders.add(order1);
        fakeOrders.add(order2);

        return fakeOrders;
    }

    private static UserEntity createUser(String username) {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername(username);
        return user;
    }

    private static DrinkEntity createDrink(String name, BigDecimal price, int volume) {
        DrinkEntity drink = new DrinkEntity();
        drink.setName(name);
        drink.setPrice(price);
        drink.setVolume(volume);
        return drink;
    }

    private static FoodEntity createFood(String name, BigDecimal price) {
        FoodEntity food = new FoodEntity();
        food.setName(name);
        food.setPrice(price);
        return food;
    }

    private static OrderEntity createOrder(UserEntity user, LocalDateTime dateTime, boolean isCompleted, boolean isPaid, List<DrinkEntity> drinks, List<FoodEntity> foods) {
        OrderEntity order = new OrderEntity();
        order.setMadeBy(user);
        order.setDateTime(dateTime);
        order.setCompleted(isCompleted);
        order.setPaid(isPaid);
        order.setDrinks(drinks);
        order.setFoods(foods);
        return order;
    }

//    @Test
//    public void testAddFood() {
//        FoodDTO foodDTO = new FoodDTO();
//        FoodEntity foodEntity = new FoodEntity();
//        when(modelMapperMock.map((foodDTO), (FoodEntity.class))).thenReturn(foodEntity);
//
//        orderService.addFood(foodDTO, false);
//
//        verify(foodServiceMock, times(1)).addFood(foodEntity);
//    }

//    @Test
//    public void testClearDrinksCache() {
//        // Act
//        orderService.clearDrinksCache();
//
//        // Assert
//        verify(eventPublisherMock, times(1)).publishEvent(any(DrinksCacheEvictEvent.class));
//    }

//    @Test
//    public void testClearFoodsCache() {
//        // Act
//        orderService.clearFoodsCache();
//
//        // Assert
//        verify(eventPublisherMock, times(1)).publishEvent(any(FoodsCacheEvictEvent.class));
//    }

//    @Test
//    public void testAddToMenuWithDrink() {
//        // Arrange
//        DrinkEntity drinkEntity = new DrinkEntity();
//        OrderEntity menu = new OrderEntity();
//        when(orderRepositoryMock.findById(1L)).thenReturn(Optional.of(menu));
//
//        // Act
//        orderService.addToMenu(drinkEntity);
//
//        // Assert
//        verify(orderRepositoryMock, times(1)).save(menu);
//    }
//
//    @Test
//    public void testAddToMenuWithFood() {
//        // Arrange
//        FoodEntity foodEntity = new FoodEntity();
//        OrderEntity menu = new OrderEntity();
//
//        when(orderRepositoryMock.findById(1L)).thenReturn(Optional.of(menu));
//
//        // Act
//        orderService.addToMenu(foodEntity);
//
//        // Assert
//        verify(orderRepositoryMock, times(1)).save(menu);
//    }

//    @Test
//    public void testAllOrdersByUsername() {
//        // Arrange
//        String username = "testUser";
//        List<OrderEntity> fakeOrders = createFakeOrders();
//        when(orderRepositoryMock.findOrderEntitiesByMadeBy_UsernameOrderByDateTimeDesc(username)).thenReturn(fakeOrders);
//
//        // Act
//        List<OrderEntity> result = orderService.allOrdersByUsername(username);
//
//        // Assert
//        assertEquals(fakeOrders, result);
//        verify(orderRepositoryMock, times(1)).findOrderEntitiesByMadeBy_UsernameOrderByDateTimeDesc(username);
//    }

//    @Test
//    public void testGetAllOrders() {
//        // Arrange
//        List<OrderEntity> fakeOrders = createFakeOrders();
//        when(orderRepositoryMock.getAllByOrderByDateTimeDesc()).thenReturn(fakeOrders);
//
//        // Act
//        List<OrderEntity> result = orderService.getAllOrders();
//
//        // Assert
//        assertEquals(fakeOrders, result);
//        verify(orderRepositoryMock, times(1)).getAllByOrderByDateTimeDesc();
//    }

//    @Test public void alabala() {
//        UserEntity pesho = new UserEntity();
//        pesho.setUsername("pesho");
//        pesho.setLastOrder(new OrderEntity());
//        when(userRepositoryMock.findByUsername(pesho.getUsername())).thenReturn(Optional.of(pesho));
//
//
//    }
}
