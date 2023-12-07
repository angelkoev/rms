package com.rms.service;

import com.rms.model.dto.RegisterDTO;
import com.rms.model.dto.UserDTO;
import com.rms.model.entity.*;
import com.rms.model.views.UserView;
import com.rms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
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
    private ModelMapper modelMapperMock;

    @Mock
    private OrderService orderServiceMock;
    @Mock
    private DrinkService drinkServiceMock;
    @Mock
    private FoodService foodServiceMock;

    @InjectMocks
    private UserService userServiceMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindUserByUsername() {
        when(userRepositoryMock.findByUsername("john")).thenReturn(Optional.of(new UserEntity()));
        when(modelMapperMock.map(any(), any())).thenReturn(new UserDTO());

        UserDTO userDTO = userServiceMock.findUserByUsername("john");

        assertNotNull(userDTO);
    }

    @Test
    public void testFindUserByEmail() {
        when(userRepositoryMock.findByEmail("john@example.com")).thenReturn(Optional.of(new UserEntity()));
        when(modelMapperMock.map(any(), any())).thenReturn(new UserDTO());

        UserDTO userDTO = userServiceMock.findUserByEmail("john@example.com");

        assertNotNull(userDTO);
    }

    @Test
    public void testRegister() {
        when(modelMapperMock.map(any(), any())).thenReturn(new UserEntity());
        when(passwordEncoderMock.encode("password")).thenReturn("encodedPassword");
        when(userRoleServiceMock.findUserRoleEntityByRole(UserRoleEnum.USER)).thenReturn(new UserRoleEntity());
        when(userRepositoryMock.save(any())).thenReturn(new UserEntity());

        RegisterDTO registerDTO = new RegisterDTO("john", "john@example.com", "password", "John", "Doe", "123456", "Address");
        userServiceMock.register(registerDTO);
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
        userServiceMock.checkLastOrder(username);

        // Assert
        verify(orderServiceMock, times(1)).createNewLastOrder(currentUser);
        verify(orderServiceMock, times(1)).saveOrder(newOrder);
        verify(userRepositoryMock, times(1)).save(currentUser);
    }

    @Test
    public void testCheckLastOrderWhenLastOrderIsNotNull() {
        // Arrange
        String username = "testUser";
        UserEntity currentUser = new UserEntity();
        currentUser.setUsername(username);
        OrderEntity existingOrder = new OrderEntity();
        currentUser.setLastOrder(existingOrder);
        when(userRepositoryMock.findByUsername(username)).thenReturn(java.util.Optional.of(currentUser));

        // Act
        userServiceMock.checkLastOrder(username);

        // Assert
        verify(orderServiceMock, never()).createNewLastOrder(currentUser);
        verify(orderServiceMock, never()).saveOrder(any(OrderEntity.class));
        verify(userRepositoryMock, never()).save(currentUser);
    }

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
        boolean isAdmin = userServiceMock.isAdmin(username);

        // Assert
        assertTrue(isAdmin);
    }

    @Test
    public void testIsAdminWhenUserDoesNotHaveAdminRole() {
        // Arrange
        String username = "testUser";
        UserEntity userWithoutAdminRole = new UserEntity();
        userWithoutAdminRole.setUsername(username);
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRole(UserRoleEnum.USER);
        userWithoutAdminRole.setRoles(Collections.singleton(userRole));
        when(userRepositoryMock.findByUsername(username)).thenReturn(java.util.Optional.of(userWithoutAdminRole));

        // Act
        boolean isAdmin = userServiceMock.isAdmin(username);

        // Assert
        assertFalse(isAdmin);
    }

    @Test
    public void testGetAllUserViews() {
        // Arrange
        UserEntity user1 = createUserWithRoles("user1", UserRoleEnum.USER);
        UserEntity user2 = createUserWithRoles("user2", UserRoleEnum.ADMIN, UserRoleEnum.USER);
        List<UserEntity> allUsers = Arrays.asList(user1, user2);
        when(userServiceMock.getAllUsersOrderById()).thenReturn(allUsers);
        UserView userView1 = createUserViewWithRoles("user1", "USER");
        UserView userView2 = createUserViewWithRoles("user2", "ADMIN", "USER");
        when(modelMapperMock.map(user1, UserView.class)).thenReturn(userView1);
        when(modelMapperMock.map(user2, UserView.class)).thenReturn(userView2);

        // Act
        List<UserView> allUserViews = userServiceMock.getAllUserViews();

        // Assert
        assertEquals(2, allUserViews.size());
    }

    @Test
    public void testTotalCurrentPrice() {
        // Arrange
        UserEntity user = createUserWithRoles("user1", UserRoleEnum.USER);
        OrderEntity order = new OrderEntity();
        DrinkEntity drink = new DrinkEntity();
        drink.setPrice(new BigDecimal("10.00"));
        order.setDrinks(Collections.singletonList(drink));
        user.setLastOrder(order);

        when(userRepositoryMock.findByUsername("user1")).thenReturn(Optional.of(user));

        // Act
        String totalOrderPrice = userServiceMock.totalCurrentPrice("user1");

        // Assert
        assertEquals("10.00", totalOrderPrice);
    }

    @Test
    public void testRemoveAdmin() {
        // Arrange
        UserEntity user = createUserWithRoles("admin1", UserRoleEnum.ADMIN);

        when(userRepositoryMock.findById(2L)).thenReturn(Optional.of(user));

        // Act
        userServiceMock.removeAdmin(2L);

        // Assert
        assertTrue(user.getRoles().isEmpty());
    }

    @Test
    public void testRemoveAdminPesho() {
        // Arrange
        UserEntity user = createUserWithRoles("admin1", UserRoleEnum.ADMIN);

        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(user));

        // Act
        userServiceMock.removeAdmin(1L);

        // Assert
        assertFalse(user.getRoles().isEmpty());
    }

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
        userServiceMock.deleteDrinkFromLastOrder("user1", 1L);

        // Assert
        assertTrue(order.getDrinks().isEmpty());
    }

    @Test
    public void testAddDrinkToLastOrderWithNullLastOrder() {
        // Arrange
        UserEntity user = createUserWithRoles("user1");
        when(userRepositoryMock.findByUsername("user1")).thenReturn(Optional.of(user));
        when(orderServiceMock.createNewLastOrder(user)).thenReturn(new OrderEntity());

        // Act
        userServiceMock.addDrinkToLastOrder("user1", 1L);

        // Assert
        assertFalse(user.getLastOrder().getDrinks().isEmpty());
    }


    @Test
    void testAddFoodToLastOrder() {
        // Arrange
        UserEntity user = createUserWithRoles("user1");
        FoodEntity food = new FoodEntity();
        food.setId(1L);
        OrderEntity order = new OrderEntity();
        user.setLastOrder(order);
        when(userRepositoryMock.findByUsername("user1")).thenReturn(Optional.of(user));
        when(foodServiceMock.findById(1L)).thenReturn(food);

        // Act
        userServiceMock.addFoodToLastOrder("user1", 1L);

        // Assert
        assertEquals(1, order.getFoods().size());
        assertEquals(food, order.getFoods().get(0));
        verify(orderServiceMock, times(1)).saveOrder(order);
    }

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
