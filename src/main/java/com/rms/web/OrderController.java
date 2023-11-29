package com.rms.web;

import com.rms.model.dto.PreorderDTO;
import com.rms.model.dto.RegisterDTO;
import com.rms.model.entity.*;
import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;
import com.rms.service.DrinkService;
import com.rms.service.FoodService;
import com.rms.service.OrderService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rms.model.entity.DrinkTypeEnum.*;
import static com.rms.model.entity.FoodTypeEnum.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final DrinkService drinkService;
    private final FoodService foodService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, DrinkService drinkService, FoodService foodService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.drinkService = drinkService;
        this.foodService = foodService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/menu")
    public String viewAll(Model model) {

        OrderEntity menu = orderService.getMenu();

        if (menu == null) {
            return "/";
        }

//        // get food by Type
//        Set<DrinkEntity> coffeeBasedDrinks = drinkService.findAllByTypeAndOrderId(COFFEE_BASED, 1);
//        List<DrinkView> coffeeBasedDrinksView = coffeeBasedDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
//        model.addAttribute("coffeeBasedDrinksView", coffeeBasedDrinksView);
//
//        Set<DrinkEntity> wineDrinks = drinkService.findAllByTypeAndOrderId(WINE, 1);
//        List<DrinkView> wineDrinksView = wineDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
//        model.addAttribute("wineDrinksView", wineDrinksView);
//
//        Set<DrinkEntity> hotBeverageDrinks = drinkService.findAllByTypeAndOrderId(HOT_BEVERAGE, 1);
//        List<DrinkView> hotBeverageDrinksView = hotBeverageDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
//        model.addAttribute("hotBeverageDrinksView", hotBeverageDrinksView);
//
//        Set<DrinkEntity> juiceDrinks = drinkService.findAllByTypeAndOrderId(JUICE, 1);
//        List<DrinkView> juiceDrinksView = juiceDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
//        model.addAttribute("juiceDrinksView", juiceDrinksView);
//
//        Set<DrinkEntity> beerDrinks = drinkService.findAllByTypeAndOrderId(BEER, 1);
//        List<DrinkView> beerDrinksView = beerDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
//        model.addAttribute("beerDrinksView", beerDrinksView);
//
//        Set<DrinkEntity> sodaDrinks = drinkService.findAllByTypeAndOrderId(SODA, 1);
//        List<DrinkView> sodaDrinksView = sodaDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
//        model.addAttribute("sodaDrinksView", sodaDrinksView);
//
//        Set<DrinkEntity> cocktailDrinks = drinkService.findAllByTypeAndOrderId(COCKTAIL, 1);
//        List<DrinkView> cocktailDrinksView = cocktailDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
//        model.addAttribute("cocktailDrinksView", cocktailDrinksView);
//
//        Set<DrinkEntity> alcoholicDrinks = drinkService.findAllByTypeAndOrderId(ALCOHOLIC, 1);
//        List<DrinkView> alcoholicDrinksView = alcoholicDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
//        model.addAttribute("alcoholicDrinksView", alcoholicDrinksView);
//
//        Set<FoodEntity> saladFoods = foodService.findAllByTypeAndOrderId(SALAD, 1);
//        List<FoodView> saladFoodsView = saladFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
//        model.addAttribute("saladFoodsView", saladFoodsView);
//
//        Set<FoodEntity> soupFoods = foodService.findAllByTypeAndOrderId(SOUP, 1);
//        List<FoodView> soupFoodsView = soupFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
//        model.addAttribute("soupFoodsView", soupFoodsView);
//
//        Set<FoodEntity> dessertFoods = foodService.findAllByTypeAndOrderId(DESSERT, 1);
//        List<FoodView> dessertFoodsView = dessertFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
//        model.addAttribute("dessertFoodsView", dessertFoodsView);
//
//        Set<FoodEntity> mainCourseFoods = foodService.findAllByTypeAndOrderId(MAIN_COURSE, 1);
//        List<FoodView> mainCourseFoodsView = mainCourseFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
//        model.addAttribute("mainCourseFoodsView", mainCourseFoodsView);
//
//        Set<FoodEntity> sandwichFoods = foodService.findAllByTypeAndOrderId(SANDWICH, 1);
//        List<FoodView> sandwichFoodsView = sandwichFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
//        model.addAttribute("sandwichFoodsView", sandwichFoodsView);
//
//        Set<FoodEntity> pizzaFoods = foodService.findAllByTypeAndOrderId(PIZZA, 1);
//        List<FoodView> pizzaFoodsView = pizzaFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
//        model.addAttribute("pizzaFoodsView", pizzaFoodsView);
//
//        Set<FoodEntity> bbqFoods = foodService.findAllByTypeAndOrderId(PIZZA, 1);
//        List<FoodView> bbqFoodsView = bbqFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
//        model.addAttribute("bbqFoodsView", bbqFoodsView);
//
//        Set<FoodEntity> vegetarianFoods = foodService.findAllByTypeAndOrderId(VEGETARIAN, 1);
//        List<FoodView> vegetarianFoodsView = vegetarianFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
//        model.addAttribute("vegetarianFoodsView", vegetarianFoodsView);
//
//        Set<FoodEntity> breadFoods = foodService.findAllByTypeAndOrderId(BREAD, 1);
//        List<FoodView> breadFoodsView = breadFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
//        model.addAttribute("breadFoodsView", breadFoodsView);

        Set<DrinkEntity> allDrinks = orderService.getMenu().getDrinks();
        List<DrinkView> allDrinksView = allDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
        model.addAttribute("allDrinksView", allDrinksView);

        Set<FoodEntity> allFoods = orderService.getMenu().getFoods();
        List<FoodView> allFoodsView = allFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
        model.addAttribute("allFoodsView", allFoodsView);

//        model.addAttribute("foodCategories", Arrays.stream(FoodTypeEnum.values()).toList());
//        model.addAttribute("drinkCategories", Arrays.stream(DrinkTypeEnum.values()).toList());

        return "menu-view";
    }

    @GetMapping("/food/{id}")
    public String addFoodToOrder(@PathVariable Long id) {

        OrderEntity menu = orderService.getMenu();

        if (menu == null) {
            return "/";
        }

        return "redirect:menu-view";
    }

    @GetMapping("/drink/{id}")
    public String addDrinkToOrder(@PathVariable Long id, PreorderDTO preorderDTO, Model model) {

        OrderEntity menu = orderService.getMenu();
        DrinkEntity currentDrink = drinkService.findById(id);
        DrinkView currendDrinkView = modelMapper.map(currentDrink, DrinkView.class);

        preorderDTO.getDrinks().add(currendDrinkView);

        model.addAttribute("preorderDrinks", preorderDTO.getDrinks());
        model.addAttribute("preorderFoods", preorderDTO.getFoods());

        Set<DrinkEntity> allDrinks = orderService.getMenu().getDrinks();
        List<DrinkView> allDrinksView = allDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
        model.addAttribute("allDrinksView", allDrinksView);

        Set<FoodEntity> allFoods = orderService.getMenu().getFoods();
        List<FoodView> allFoodsView = allFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
        model.addAttribute("allFoodsView", allFoodsView);

//        if (menu == null) {
//            return "/";
//        }

        return "menu-view";
    }

//    @PostMapping("/order/food/id")
//    public String addToOrder(Model model) {
//
//        OrderEntity menu = orderService.getMenu();
//
//        if (menu == null) {
//            return "/";
//        }
//
//        return "redirect:menu-view";
//    }

    @GetMapping("/new")
    public String newOrder(Model model) {

        OrderEntity menu = orderService.getMenu();


        return "/order-view";

//        @Valid RegisterDTO registerDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//
//            boolean equalPasswords = registerDTO.getPassword().equals(registerDTO.getConfirmPassword());
//
//            if (!equalPasswords) {
//                bindingResult.addError(new FieldError("registerDTO", "confirmPassword", "Паролите НЕ съвпадат !!!"));
//            }
//
//            if (bindingResult.hasErrors() || !equalPasswords) {
//                redirectAttributes.addFlashAttribute("registerDTO", registerDTO);
//                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);
//
//                return "redirect:/users/register";
//            }
//
//            this.userService.register(registerDTO);
//
//            // FIXME message that user is registered and to go to login page to Login !!!
//            return "redirect:/home";
    }
}