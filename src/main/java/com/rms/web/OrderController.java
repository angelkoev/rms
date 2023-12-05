package com.rms.web;

import com.rms.model.dto.DrinkDTO;
import com.rms.model.dto.FoodDTO;
import com.rms.model.entity.*;
import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;
import com.rms.model.views.OrderView;
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
import java.util.ArrayList;
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
//        UserEntity currentUser = userService.getUserByUsername(authentication.getName());
//
//        if (currentUser.getLastOrder() == null) {
//            OrderEntity newLastOrder = orderService.createNewLastOrder(currentUser);
//            currentUser.setLastOrder(newLastOrder);
//        }

        String username = authentication.getName();
        userService.checkLastOrder(username);
//
//        List<DrinkEntity> currentDrinks = currentUser.getLastOrder().getDrinks();
//        List<DrinkView> allCurrentDrinkViews = currentDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();

        List<DrinkView> allCurrentDrinkViews = userService.getAllCurrentDrinkViews(username);
        model.addAttribute("allCurrentDrinkViews", allCurrentDrinkViews);
        model.addAttribute("drinkViewCount", allCurrentDrinkViews.size());

//        List<FoodEntity> currentFoods = currentUser.getLastOrder().getFoods();
//        List<FoodView> allCurrentFoodViews = currentFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();

        List<FoodView> allCurrentFoodViews = userService.getAllCurrentFoodViews(username);
        model.addAttribute("allCurrentFoodViews", allCurrentFoodViews);
        model.addAttribute("foodViewCount", allCurrentFoodViews.size());

        model.addAttribute("totalViewSize", allCurrentDrinkViews.size() + allCurrentFoodViews.size());

//        BigDecimal priceForAllDrinks = allCurrentDrinkViews.stream()
//                .map(DrinkView::getPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal priceForAllFoods = allCurrentFoodViews.stream()
//                .map(FoodView::getPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        String totalOrderPrice = priceForAllDrinks.add(priceForAllFoods).toString();

        String totalOrderPrice = userService.totalCurrentPrice(username);
        model.addAttribute("totalOrderPrice", totalOrderPrice);

//        List<DrinkEntity> allDrinks = orderService.getMenu().getDrinks();
//        List<DrinkView> allDrinksView = allDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
        List<DrinkView> allDrinksView = orderService.getAllDrinksView(username);
        model.addAttribute("allDrinksView", allDrinksView);


//        List<FoodEntity> allFoods = orderService.getMenu().getFoods();
//        List<FoodView> allFoodsView = allFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
        List<FoodView> allFoodsView = orderService.getAllFoodsView(username);
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

    @GetMapping("/history")
    public String ordersHistory(Model model, RedirectAttributes redirectAttributes) {

        boolean isAdmin = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = userService.getUserByUsername(authentication.getName());

        for (UserRoleEntity role : currentUser.getRoles()) {
            if (role.getRole().name().equals("ADMIN")) {
                isAdmin = true;
                break;
            }
        }

        if (currentUser.getLastOrder() == null && !isAdmin) {
            String infoMessage = "Нямате направени поръчки!";
            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            return "redirect:/home";
        }
//            OrderEntity newLastOrder = orderService.createNewLastOrder(currentUser);
//            orderService.saveOrder(newLastOrder);
//            currentUser.setLastOrder(newLastOrder);
//        }

        List<OrderEntity> orderEntities;
        if (isAdmin) {
            orderEntities = orderService.getAllOrders();
        } else {
            orderEntities = orderService.allOrdersByUsername(authentication.getName());
        }

        if (!isAdmin) {
            Long idForLastOrder = currentUser.getLastOrder().getId();
            OrderEntity lastOrder = orderEntities.stream().filter(o -> o.getId().equals(idForLastOrder)).findFirst().orElse(null);
            if (lastOrder != null) {
                orderEntities.remove(lastOrder);
            }
        } else {
            OrderEntity menu = orderEntities.stream().filter(o -> o.getId() == 1L).findFirst().orElse(null);
            if (menu != null) {
                orderEntities.remove(menu);
            }
        }

        List<OrderView> allCurrentOrdersViews = orderEntities.stream().map(orderEntity -> {
            OrderView currentOrderView = modelMapper.map(orderEntity, OrderView.class);

            currentOrderView.setMadeBy(orderEntity.getMadeBy().getUsername());

            BigDecimal currentPriceForAllDrinks = orderEntity.getDrinks().stream().map(DrinkEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal currentPriceForAllFoods = orderEntity.getFoods().stream().map(FoodEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalOrderPrice = currentPriceForAllDrinks.add(currentPriceForAllFoods);

            currentOrderView.setTotalPrice(totalOrderPrice);

            String currentDateTime = orderEntity.getDateTime().toString(); // 2023-12-04T12:17:56.705155
            currentOrderView.setDate(currentDateTime.split("T")[0]);
            currentOrderView.setTime(currentDateTime.split("T")[1]);

            return currentOrderView;
        }).toList();

        List<OrderView> filteredOrdersWithZeroAmount = allCurrentOrdersViews.stream().filter(order -> order.getTotalPrice().compareTo(BigDecimal.ZERO) != 0).toList();

        model.addAttribute("allCurrentOrdersViews", filteredOrdersWithZeroAmount);

        return "order-history";
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