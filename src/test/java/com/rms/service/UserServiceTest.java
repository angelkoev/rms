package com.rms.service;

import com.rms.model.dto.RegisterDTO;
import com.rms.model.dto.UserDTO;
import com.rms.model.entity.*;
import com.rms.model.views.OrderView;
import com.rms.model.views.UserView;
import com.rms.repository.OrderRepository;
import com.rms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private UserRoleService userRoleServiceMock;
    @Mock
    private OrderRepository orderRepositoryMock;

    @Mock
    private ModelMapper modelMapperMock;

    @Mock
    private OrderService orderServiceMock;
    @Mock
    private DrinkService drinkServiceMock;
    @Mock
    private FoodService foodServiceMock;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindUserByUsername() {
        when(userRepositoryMock.findByUsername("john")).thenReturn(Optional.of(new UserEntity()));
        when(modelMapperMock.map(any(), any())).thenReturn(new UserDTO());

        UserDTO userDTO = userService.findUserByUsername("john");

        assertNotNull(userDTO);
    }

//    @Test
//    public void testFindUserByEmail() {
//        when(userRepositoryMock.findByEmail("john@example.com")).thenReturn(Optional.of(new UserEntity()));
//        when(modelMapperMock.map(any(), any())).thenReturn(new UserDTO());
//
//        UserDTO userDTO = userService.findUserByEmail("john@example.com");
//
//        assertNotNull(userDTO);
//    }

    @Test
    public void testRegister() {
        when(modelMapperMock.map(any(), any())).thenReturn(new UserEntity());
        when(passwordEncoderMock.encode("password")).thenReturn("encodedPassword");
        when(userRoleServiceMock.findUserRoleEntityByRole(UserRoleEnum.USER)).thenReturn(new UserRoleEntity());
        when(userRepositoryMock.save(any())).thenReturn(new UserEntity());

        RegisterDTO registerDTO = new RegisterDTO("john", "john@example.com", "password", "John", "Doe", "123456", "Address");
        userService.register(registerDTO);
    }

    @Test
    public void testCheckLastOrderWhenLastOrderIsNull() {
        // Arrange
        String username = "testUser";
        UserEntity currentUser = new UserEntity();
        currentUser.setUsername(username);
        when(userRepositoryMock.findByUsername(username)).thenReturn(java.util.Optional.of(currentUser));
        OrderEntity newOrder = new OrderEntity();
        when(orderServiceMock.createNewLastOrder(currentUser)).thenReturn(newOrder);

        // Act
        userService.checkLastOrder(username);

        // Assert
        verify(orderServiceMock, times(1)).createNewLastOrder(currentUser);
        verify(orderServiceMock, times(1)).saveOrder(newOrder);
        verify(userRepositoryMock, times(1)).save(currentUser);
    }

//    @Test
//    public void testCheckLastOrderWhenLastOrderIsNotNull() {
//        // Arrange
//        String username = "testUser";
//        UserEntity currentUser = new UserEntity();
//        currentUser.setUsername(username);
//        OrderEntity existingOrder = new OrderEntity();
//        currentUser.setLastOrder(existingOrder);
//        when(userRepositoryMock.findByUsername(username)).thenReturn(java.util.Optional.of(currentUser));
//
//        // Act
//        userService.checkLastOrder(username);
//
//        // Assert
//        verify(orderServiceMock, never()).createNewLastOrder(currentUser);
//        verify(orderServiceMock, never()).saveOrder(any(OrderEntity.class));
//        verify(userRepositoryMock, never()).save(currentUser);
//    }

    @Test
    public void testIsAdminWhenUserHasAdminRole() {
        // Arrange
        String username = "testUser";
        UserEntity userWithAdminRole = new UserEntity();
        userWithAdminRole.setUsername(username);
        UserRoleEntity adminRole = new UserRoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);
        userWithAdminRole.setRoles(Collections.singleton(adminRole));
        when(userRepositoryMock.findByUsername(username)).thenReturn(java.util.Optional.of(userWithAdminRole));

        // Act
        boolean isAdmin = userService.isAdmin(username);

        // Assert
        assertTrue(isAdmin);
    }

//    @Test
//    public void testIsAdminWhenUserDoesNotHaveAdminRole() {
//        // Arrange
//        String username = "testUser";
//        UserEntity userWithoutAdminRole = new UserEntity();
//        userWithoutAdminRole.setUsername(username);
//        UserRoleEntity userRole = new UserRoleEntity();
//        userRole.setRole(UserRoleEnum.USER);
//        userWithoutAdminRole.setRoles(Collections.singleton(userRole));
//        when(userRepositoryMock.findByUsername(username)).thenReturn(java.util.Optional.of(userWithoutAdminRole));
//
//        // Act
//        boolean isAdmin = userService.isAdmin(username);
//
//        // Assert
//        assertFalse(isAdmin);
//    }

    @Test
    public void testGetAllUserViews() {
        // Arrange
        UserEntity user1 = createUserWithRoles("user1", UserRoleEnum.USER);
        UserEntity user2 = createUserWithRoles("user2", UserRoleEnum.ADMIN, UserRoleEnum.USER);
        List<UserEntity> allUsers = Arrays.asList(user1, user2);
        when(userService.getAllUsersOrderById()).thenReturn(allUsers);
        UserView userView1 = createUserViewWithRoles("user1", "USER");
        UserView userView2 = createUserViewWithRoles("user2", "ADMIN", "USER");
        when(modelMapperMock.map(user1, UserView.class)).thenReturn(userView1);
        when(modelMapperMock.map(user2, UserView.class)).thenReturn(userView2);

        // Act
        List<UserView> allUserViews = userService.getAllUserViews();

        // Assert
        assertEquals(2, allUserViews.size());
    }

//    @Test
//    public void testTotalCurrentPrice() {
//        // Arrange
//        UserEntity user = createUserWithRoles("user1", UserRoleEnum.USER);
//        OrderEntity order = new OrderEntity();
//        DrinkEntity drink = new DrinkEntity();
//        drink.setPrice(new BigDecimal("10.00"));
//        order.setDrinks(Collections.singletonList(drink));
//        user.setLastOrder(order);
//
//        when(userRepositoryMock.findByUsername("user1")).thenReturn(Optional.of(user));
//
//        // Act
//        String totalOrderPrice = userService.totalCurrentPrice("user1");
//
//        // Assert
//        assertEquals("10.00", totalOrderPrice);
//    }

//    @Test
//    public void testRemoveAdmin() {
//        // Arrange
//        UserEntity user = createUserWithRoles("admin1", UserRoleEnum.ADMIN);
//        when(userRepositoryMock.findById(2L)).thenReturn(Optional.of(user));
//
//        // Act
//        userService.removeAdmin(2L);
//
//        // Assert
//        assertTrue(user.getRoles().isEmpty());
//    }

//    @Test
//    public void testRemoveAdminPesho() {
//        // Arrange
//        UserEntity user = createUserWithRoles("admin1", UserRoleEnum.ADMIN);
//        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(user));
//
//        // Act
//        userService.removeAdmin(1L);
//
//        // Assert
//        assertFalse(user.getRoles().isEmpty());
//    }

    @Test
    public void testDeleteDrinkFromLastOrder() {
        // Arrange
        UserEntity user = createUserWithRoles("user1");
        DrinkEntity drink = new DrinkEntity();
        drink.setId(1L);
        OrderEntity order = new OrderEntity();
        List<DrinkEntity> drinks = new ArrayList<>();
        drinks.add(drink);
        order.setDrinks(drinks);
        user.setLastOrder(order);
        when(userRepositoryMock.findByUsername("user1")).thenReturn(Optional.of(user));
        doNothing().when(orderServiceMock).saveOrder(order);

        // Act
        userService.deleteDrinkFromLastOrder("user1", 1L);

        // Assert
        assertTrue(order.getDrinks().isEmpty());
    }

    @Test
    public void testDeleteFoodFromLastOrder() {
        // Arrange
        UserEntity user = createUserWithRoles("user1");
        FoodEntity food = new FoodEntity();
        food.setId(1L);
        OrderEntity order = new OrderEntity();
        List<FoodEntity> foods = new ArrayList<>();
        foods.add(food);
        order.setFoods(foods);
        user.setLastOrder(order);
        when(userRepositoryMock.findByUsername("user1")).thenReturn(Optional.of(user));
        doNothing().when(orderServiceMock).saveOrder(order);

        // Act
        userService.deleteFoodFromLastOrder("user1", 1L);

        // Assert
        assertTrue(order.getFoods().isEmpty());
    }

//    @Test
//    public void testAddDrinkToLastOrderWithNullLastOrder() {
//        // Arrange
//        UserEntity user = createUserWithRoles("user1");
//        when(userRepositoryMock.findByUsername("user1")).thenReturn(Optional.of(user));
//        when(orderServiceMock.createNewLastOrder(user)).thenReturn(new OrderEntity());
//
//        // Act
//        userService.addDrinkToLastOrder("user1", 1L);
//
//        // Assert
//        assertFalse(user.getLastOrder().getDrinks().isEmpty());
//    }


    @Test
    void testAddFoodToLastOrder() {
        // Arrange
        UserEntity user = createUserWithRoles("user1");
        FoodEntity food = new FoodEntity();
        food.setId(1L);
        OrderEntity lastOrder = new OrderEntity();
        user.setLastOrder(lastOrder);
        when(userRepositoryMock.findByUsername("user1")).thenReturn(Optional.of(user));
        when(foodServiceMock.findById(1L)).thenReturn(food);

        // Act
        userService.addFoodToLastOrder("user1", 1L);

        // Assert
        assertEquals(1, lastOrder.getFoods().size());
        assertEquals(food, lastOrder.getFoods().get(0));
        verify(orderServiceMock, times(1)).saveOrder(lastOrder);
    }

    @Test
    void getAllCurrentOrdersViews() {
        UserEntity user = createUserWithRoles("user1", UserRoleEnum.ADMIN);
        FoodEntity food = new FoodEntity();
        food.setId(1L);
        food.setPrice(BigDecimal.ONE);
        OrderEntity order1 = new OrderEntity();
        order1.setId(2L);
        order1.setDateTime(LocalDateTime.now());
        order1.getFoods().add(food);
        order1.setMadeBy(user);
        OrderEntity order2 = new OrderEntity();
        order2.setId(3L);
        order2.setDateTime(LocalDateTime.now());
        order2.getFoods().add(food);
        order2.setMadeBy(user);
//        user.setLastOrder(order1);
        user.getOrders().add(order1);

        OrderView orderView = new OrderView();
        orderView.setTime("0");
        orderView.setDate("0");

        when(userRepositoryMock.findByUsername("user1")).thenReturn(Optional.of(user));
        when(foodServiceMock.findById(1L)).thenReturn(food);
        when(orderRepositoryMock.findOrderEntitiesByMadeBy_UsernameOrderByDateTimeDesc("user1")).thenReturn(List.of(order1, order2));
        when(orderServiceMock.getAllOrders()).thenReturn(List.of(order1, order2));
        when(modelMapperMock.map(any(), any())).thenReturn(orderView);

        List<OrderView> allCurrentOrdersViews = userService.getAllCurrentOrdersViews(user.getUsername());

        assertEquals(2, allCurrentOrdersViews.size());

    }

//    @Test
//    void addAdmin() {
//        UserEntity user = createUserWithRoles("user1", UserRoleEnum.ADMIN);
//        user.setId(1L);
//        when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.of(user));
//        when(userRepositoryMock.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
////        when(userService.findUserEntityByUsername(user.getUsername())).thenReturn(Optional.of(user));
//
//        UserDetails userDetails = new UserDetails
//
//        doNothing(userService.addAdmin(user.getId()));
//        verify(userRepositoryMock, times(1)).save(user);
//    }

        private UserEntity createUserWithRoles(String username, UserRoleEnum... roles) {
        UserEntity user = new UserEntity();
        user.setUsername(username);

        for (UserRoleEnum role : roles) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setRole(role);
            user.getRoles().add(userRoleEntity);
        }
        return user;
    }

    private UserView createUserViewWithRoles(String username, String... roles) {
        UserView user = new UserView();
        user.setUsername(username);

        for (String role : roles) {
            user.getRoles().add(role);
        }

        return user;
    }

}
