package com.rms.web;

import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.OrderEntity;
import com.rms.model.entity.UserEntity;
import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;
import com.rms.service.DrinkService;
import com.rms.service.FoodService;
import com.rms.service.OrderService;
import com.rms.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final DrinkService drinkService;
    private final FoodService foodService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public OrderController(OrderService orderService, DrinkService drinkService, FoodService foodService, ModelMapper modelMapper, UserService userService) {
        this.orderService = orderService;
        this.drinkService = drinkService;
        this.foodService = foodService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @GetMapping("/menu")
    public String viewAll(Model model) {

        OrderEntity menu = orderService.getMenu();

        if (menu == null) {
            return "/";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = userService.getUserByUsername(authentication.getName());

        if (currentUser.getLastOrder() == null) {
            OrderEntity newLastOrder = orderService.createNewLastOrder(currentUser);
            currentUser.setLastOrder(newLastOrder);
        }
        List<DrinkEntity> currentDrinks = currentUser.getLastOrder().getDrinks();
        List<DrinkView> allCurrentDrinkViews = currentDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
        model.addAttribute("allCurrentDrinkViews", allCurrentDrinkViews);
        model.addAttribute("drinkViewCount", allCurrentDrinkViews.size());

        List<FoodEntity> currentFoods = currentUser.getLastOrder().getFoods();
        List<FoodView> allCurrentFoodViews = currentFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
        model.addAttribute("allCurrentFoodViews", allCurrentFoodViews);
        model.addAttribute("foodViewCount", allCurrentFoodViews.size());

        model.addAttribute("totalViewSize", allCurrentDrinkViews.size() + allCurrentFoodViews.size());

        List<DrinkEntity> allDrinks = orderService.getMenu().getDrinks();
        List<DrinkView> allDrinksView = allDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
        model.addAttribute("allDrinksView", allDrinksView);

        List<FoodEntity> allFoods = orderService.getMenu().getFoods();
        List<FoodView> allFoodsView = allFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
        model.addAttribute("allFoodsView", allFoodsView);

        return "menu-view";
    }

    @DeleteMapping("/drink/{id}")
    public String deleteDrinkFromOrder(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = userService.getUserByUsername(authentication.getName());

        if (currentUser.getLastOrder() == null) {
            // FIXME throw error !!!
        }

        OrderEntity lastOrder = currentUser.getLastOrder();

        DrinkEntity currentDrink = lastOrder.getDrinks().stream().filter(d -> d.getId().equals(id)).findAny().get();
        lastOrder.getDrinks().remove(currentDrink);
        orderService.saveOrder(lastOrder);
        userService.saveUser(currentUser);

        return "redirect:/order/menu";
    }

    @GetMapping("/drink/{id}")
    public String addDrinkToOrder(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = userService.getUserByUsername(authentication.getName());

        if (currentUser.getLastOrder() == null) {
            OrderEntity newLastOrder = orderService.createNewLastOrder(currentUser);
            currentUser.setLastOrder(newLastOrder);
        }

        DrinkEntity currentDrink = drinkService.findById(id);
        OrderEntity lastOrder = currentUser.getLastOrder();
        lastOrder.getDrinks().add(currentDrink);
        orderService.saveOrder(lastOrder);
        userService.saveUser(currentUser);

        return "redirect:/order/menu";
    }

    @GetMapping("/food/{id}")
    public String addFoodToOrder(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = userService.getUserByUsername(authentication.getName());

        if (currentUser.getLastOrder() == null) {
            OrderEntity newLastOrder = orderService.createNewLastOrder(currentUser);
            currentUser.setLastOrder(newLastOrder);
        }

        FoodEntity currentFood = foodService.findById(id);
        OrderEntity lastOrder = currentUser.getLastOrder();
        lastOrder.getFoods().add(currentFood);
        orderService.saveOrder(lastOrder);
        userService.saveUser(currentUser);

        return "redirect:/order/menu";
    }

    @DeleteMapping("/food/{id}")
    public String deleteFoodFromOrder(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = userService.getUserByUsername(authentication.getName());

        if (currentUser.getLastOrder() == null) {
            // FIXME throw error !!!
        }

        OrderEntity lastOrder = currentUser.getLastOrder();

        FoodEntity currentFood = lastOrder.getFoods().stream().filter(f -> f.getId().equals(id)).findAny().get();
        lastOrder.getFoods().remove(currentFood);
        orderService.saveOrder(lastOrder);
        userService.saveUser(currentUser);

        return "redirect:/order/menu";
    }


    @GetMapping("/new")
    public String newOrder(Model model) {

        OrderEntity menu = orderService.getMenu();


        return "/order-view";
    }

}