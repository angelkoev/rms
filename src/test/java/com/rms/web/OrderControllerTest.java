package com.rms.web;

import com.rms.model.dto.DrinkDTO;
import com.rms.model.entity.DrinkTypeEnum;
import com.rms.service.Impl.DrinkServiceImpl;
import com.rms.service.Impl.FoodServiceImpl;
import com.rms.service.Impl.OrderServiceImpl;
import com.rms.service.Impl.UserServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private OrderServiceImpl orderServiceImpl;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @MockBean
    private FoodServiceImpl foodServiceImpl;

    @MockBean
    private DrinkServiceImpl drinkServiceImpl;

    @Before("")
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testViewAll() throws Exception {
        when(orderServiceImpl.isMenuOK()).thenReturn(true);

        when(userServiceImpl.getAllCurrentDrinkViews("testUser")).thenReturn(Collections.emptyList());
        when(userServiceImpl.getAllCurrentFoodViews("testUser")).thenReturn(Collections.emptyList());
        when(userServiceImpl.totalCurrentPrice("testUser")).thenReturn("100.00");
        when(orderServiceImpl.getAllDrinksView()).thenReturn(Collections.emptyList());
        when(orderServiceImpl.getAllFoodsView()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/order/menu"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("menu-view"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("allCurrentDrinkViews"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("drinkViewCount"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("allCurrentFoodViews"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("foodViewCount"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("totalViewSize"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("totalOrderPrice"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("allDrinksView"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("allFoodsView"));
    }

    @Test
    @WithMockUser(username = "adminUser", roles = {"ADMIN", "USER"})
//    @WithMockUser(username = "adminUser", roles = "ADMIN")
//    @WithMockUser(username = "adminUser", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void addNewDrink_ShouldAddDrinkSuccessfully() throws Exception {
        // Arrange
        DrinkDTO drinkDTO = new DrinkDTO();
        drinkDTO.setName("New Drink");
        drinkDTO.setType(DrinkTypeEnum.BEER);
        drinkDTO.setPrice(BigDecimal.ONE);
        drinkDTO.setVolume(500);
        drinkDTO.setAddToMenu(true);

        // Mock the necessary service methods
        when(drinkServiceImpl.isDrinkAlreadyAdded(any(DrinkDTO.class))).thenReturn(false);

        // Act
        ResultActions result = mockMvc.perform(post("/order/add/drink")
                .param("name", drinkDTO.getName())
                .param("price", drinkDTO.getPrice().toString())
                .param("volume", String.valueOf(drinkDTO.getVolume()))
                .param("type", drinkDTO.getType().toString())
                .param("addToMenu", String.valueOf(drinkDTO.isAddToMenu()))
                .with(csrf()));

        // Assert
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithMockUser(username = "adminUser", roles = "ADMIN")
    void addNewDrink_ShouldRedirectWithErrorWhenDrinkAlreadyAdded() throws Exception {

        // Arrange
        DrinkDTO drinkDTO = new DrinkDTO();
        drinkDTO.setName("Existing Drink");
        drinkDTO.setAddToMenu(true);

        when(drinkServiceImpl.isDrinkAlreadyAdded(any(DrinkDTO.class))).thenReturn(true);

        // Act
        ResultActions result = mockMvc.perform(post("/order/add/drink")
                .param("name", drinkDTO.getName())
                .param("addToMenu", String.valueOf(drinkDTO.isAddToMenu()))
                .with(csrf()));

        // Assert
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/add/drink"));

    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testOrdersHistoryForUserWithNoOrders() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"))
                .andExpect(flash().attribute("infoMessage", "Нямате направени поръчки!"));
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = "ADMIN")
    public void testOrdersHistoryForAdminWithNoOrders() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"))
                .andExpect(flash().attribute("infoMessage", "Нямате направени поръчки!"));
    }

}
