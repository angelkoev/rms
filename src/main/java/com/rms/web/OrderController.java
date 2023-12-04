package com.rms.web;

import com.rms.model.dto.DrinkDTO;
import com.rms.model.dto.FoodDTO;
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
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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


        BigDecimal priceForAllDrinks = allCurrentDrinkViews.stream()
                .map(DrinkView::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal priceForAllFoods = allCurrentFoodViews.stream()
                .map(FoodView::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String totalOrderPrice = priceForAllDrinks.add(priceForAllFoods).toString();
        model.addAttribute("totalOrderPrice", totalOrderPrice);

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

        Optional<DrinkEntity> currentDrink = lastOrder.getDrinks().stream().filter(d -> d.getId().equals(id)).findAny();

        DrinkEntity drinkEntity = null;
        if (currentDrink.isPresent()) {
            drinkEntity = currentDrink.get();
        }
        //FIXME java.util.NoSuchElementException: No value present

        if (drinkEntity != null) {
            lastOrder.getDrinks().remove(drinkEntity);
        }
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

    @GetMapping("/add/drink")
    String addDrink() {

        return "add-drink";
    }

    @PostMapping("/add/drink")
    String addNewDrink(@Valid DrinkDTO drinkDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        boolean alreadyAdded = drinkService.isDrinkAlreadyAdded(drinkDTO);

        String infoMessage = "";
        if (bindingResult.hasErrors() || alreadyAdded) {
            redirectAttributes.addFlashAttribute("drinkDTO", drinkDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.drinkDTO", bindingResult);
            if (alreadyAdded) {
                infoMessage = "Вече има добавена напитка с това име!";
                redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            }
            return "redirect:/order/add/drink";
        }

        this.orderService.addDrink(drinkDTO, drinkDTO.isAddToMenu());

        // FIXME message that user is registered and to go to login page to Login !!!
        infoMessage = "Напитката беше добавена успешно!";
        redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
        return "redirect:/home";
    }

    @GetMapping("/add/food")
    String addFoodk() {

        return "add-food";
    }

    @PostMapping("/add/food")
    String addNewDrink(@Valid FoodDTO foodDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        boolean alreadyAdded = foodService.isFoodAlreadyAdded(foodDTO);

        String infoMessage = "";
        if (bindingResult.hasErrors() || alreadyAdded) {
            redirectAttributes.addFlashAttribute("foodDTO", foodDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.foodDTO", bindingResult);
            if (alreadyAdded) {
                infoMessage = "Вече има добавена храна с това име!";
                redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            }
            return "redirect:/order/add/food";
        }

        this.orderService.addFood(foodDTO, foodDTO.isAddToMenu());

        // FIXME message that user is registered and to go to login page to Login !!!
        infoMessage = "Храната беше добавена успешно!";
        redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
        return "redirect:/home";
    }


    @PostMapping("/new")
    public String newOrder(RedirectAttributes redirectAttributes) {

//        OrderEntity menu = orderService.getMenu();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = userService.getUserByUsername(authentication.getName());

        OrderEntity newOrder = orderService.makeNewOrder(currentUser);
        currentUser.getOrders().add(newOrder);

        currentUser.getLastOrder().getDrinks().clear();
        currentUser.getLastOrder().getFoods().clear();
        orderService.saveOrder(currentUser.getLastOrder());
//        OrderEntity newLastOrder = orderService.createNewLastOrder(currentUser);
//        orderService.saveOrder(newLastOrder);
//        currentUser.setLastOrder(newLastOrder);
        userService.saveUser(currentUser);

        String infoMessage = "Поръчката беше направена успешно!";
        redirectAttributes.addFlashAttribute("infoMessage", infoMessage);

        return "redirect:/home";
    }

    @ModelAttribute
    public DrinkDTO drinkDTO() {
        return new DrinkDTO();
    }

    @ModelAttribute
    public FoodDTO foodDTO() {
        return new FoodDTO();
    }

}