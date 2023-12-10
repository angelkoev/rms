package com.rms.service;

import com.rms.model.entity.*;
import com.rms.model.views.OrderView;
import com.rms.repository.*;
import com.rms.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceImplIntegrationTest {

    @Autowired
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private FoodRepository foodRepository;

    private UserEntity testUssr;
    private UserRoleEntity userRoleUSER;
    private UserRoleEntity userRoleADMIN;
    private DrinkEntity drink1;
    private FoodEntity food1;
    private OrderEntity lastOrder;
    private OrderEntity menu;

    @Test
    public void testGetAllCurrentOrdersViews() {
        userRoleUSER = userRoleRepository.findUserRoleEntityByRole(UserRoleEnum.USER);
        userRoleADMIN = userRoleRepository.findUserRoleEntityByRole(UserRoleEnum.ADMIN);

        lastOrder = new OrderEntity();
        lastOrder.setDrinks(new ArrayList<>());
        lastOrder.setFoods(new ArrayList<>());
        lastOrder.setDateTime(LocalDateTime.now());

        menu = new OrderEntity();
        menu.setDrinks(new ArrayList<>());
        menu.setFoods(new ArrayList<>());
        menu.setDateTime(LocalDateTime.now());

        drink1 = new DrinkEntity();
        drink1.setId(1L);
        drink1.setVolume(100);
        drink1.setPrice(BigDecimal.ONE);
        drink1.setName("testDrink");
        drink1.setType(DrinkTypeEnum.BEER);
        drinkRepository.save(drink1);

        food1 = new FoodEntity();
        food1.setId(1L);
        food1.setKcal(BigDecimal.ZERO);
        food1.setName("testFood");
        food1.setPrice(BigDecimal.ONE);
        food1.setType(FoodTypeEnum.BBQ);
        foodRepository.save(food1);

        testUssr = new UserEntity();
        testUssr.setUsername("testUser");
        testUssr.setEmail("testmail@test.test");
        testUssr.setPassword("testpassword");
        testUssr.setPhone("11111111");
        testUssr.setRoles(new HashSet<>());
        testUssr.setRegistrationDate(LocalDate.now());
        userRepository.save(testUssr);

        menu.setId(1L);
        menu.setMadeBy(testUssr);
        lastOrder.setId(2L);
        lastOrder.setMadeBy(testUssr);
        testUssr.setLastOrder(lastOrder);
        testUssr.getOrders().add(menu);
        testUssr.getOrders().add(lastOrder);
        orderRepository.save(lastOrder);

        lastOrder.getDrinks().add(drink1);
        lastOrder.getFoods().add(food1);
        menu.getDrinks().add(drink1);
        menu.getFoods().add(food1);
        orderRepository.save(menu);
        testUssr.getRoles().add(userRoleUSER);

        userRepository.save(testUssr);

        testUssr.getRoles().add(userRoleADMIN);

        List<OrderView> result = userServiceImpl.getAllCurrentOrdersViews(testUssr.getUsername());

        assertEquals(1, result.size());
    }

//    @BeforeEach
//    public void init() {
//        userRoleUSER = userRoleRepository.findUserRoleEntityByRole(UserRoleEnum.USER);
//        userRoleADMIN = userRoleRepository.findUserRoleEntityByRole(UserRoleEnum.ADMIN);
//
//        lastOrder = new OrderEntity();
//        lastOrder.setDrinks(new ArrayList<>());
//        lastOrder.setFoods(new ArrayList<>());
//        lastOrder.setDateTime(LocalDateTime.now());
//
//        menu = new OrderEntity();
//        menu.setDrinks(new ArrayList<>());
//        menu.setFoods(new ArrayList<>());
//        menu.setDateTime(LocalDateTime.now());
//
//        drink1 = new DrinkEntity();
//        drink1.setId(1L);
//        drink1.setVolume(100);
//        drink1.setPrice(BigDecimal.ONE);
//        drink1.setName("testDrink");
//        drink1.setType(DrinkTypeEnum.BEER);
//        drinkRepository.save(drink1);
//
//        food1 = new FoodEntity();
//        food1.setId(1L);
//        food1.setKcal(BigDecimal.ZERO);
//        food1.setName("testFood");
//        food1.setPrice(BigDecimal.ONE);
//        food1.setType(FoodTypeEnum.BBQ);
//        foodRepository.save(food1);
//
//        testUssr = new UserEntity();
//        testUssr.setUsername("testUser");
//        testUssr.setEmail("testmail@test.test");
//        testUssr.setPassword("testpassword");
//        testUssr.setPhone("11111111");
//        testUssr.setRoles(new HashSet<>());
//        testUssr.setRegistrationDate(LocalDate.now());
//        userRepository.save(testUssr);
//
//        menu.setId(1L);
//        menu.setMadeBy(testUssr);
//        lastOrder.setId(2L);
//        lastOrder.setMadeBy(testUssr);
//        testUssr.setLastOrder(lastOrder);
//        testUssr.getOrders().add(menu);
//        testUssr.getOrders().add(lastOrder);
//        orderRepository.save(lastOrder);
//
//        lastOrder.getDrinks().add(drink1);
//        lastOrder.getFoods().add(food1);
//        menu.getDrinks().add(drink1);
//        menu.getFoods().add(food1);
//        orderRepository.save(menu);
//        testUssr.getRoles().add(userRoleUSER);
//
//        userRepository.save(testUssr);
//
//        testUssr.getRoles().add(userRoleADMIN);
//
//    }


//    @Test
//    public void testGetUserByUsername() {

//        UserEntity retrievedUser = userService.getUserByUsername("testUser");
//
//        assertNotNull(retrievedUser);
//        assertEquals("testUser", retrievedUser.getUsername());
//    }
}