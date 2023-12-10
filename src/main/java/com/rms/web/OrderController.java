package com.rms.web;

import com.rms.model.dto.DrinkDTO;
import com.rms.model.dto.FoodDTO;
import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;
import com.rms.model.views.OrderView;
import com.rms.service.Impl.DrinkServiceImpl;
import com.rms.service.Impl.FoodServiceImpl;
import com.rms.service.Impl.OrderServiceImpl;
import com.rms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;
    private final DrinkServiceImpl drinkServiceImpl;
    private final FoodServiceImpl foodServiceImpl;
    private final UserService userService;

    public OrderController(OrderServiceImpl orderServiceImpl, DrinkServiceImpl drinkServiceImpl, FoodServiceImpl foodServiceImpl, UserService userService) {
        this.orderServiceImpl = orderServiceImpl;
        this.drinkServiceImpl = drinkServiceImpl;
        this.foodServiceImpl = foodServiceImpl;
        this.userService = userService;
    }

    @GetMapping("/menu")
    public String viewAll(Model model) {

        boolean isMenuOK = orderServiceImpl.isMenuOK();

        if (!isMenuOK) {
            orderServiceImpl.getMenu();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userService.checkLastOrder(username);

        List<DrinkView> allCurrentDrinkViews = userService.getAllCurrentDrinkViews(username);
        model.addAttribute("allCurrentDrinkViews", allCurrentDrinkViews);
        model.addAttribute("drinkViewCount", allCurrentDrinkViews.size());

        List<FoodView> allCurrentFoodViews = userService.getAllCurrentFoodViews(username);
        model.addAttribute("allCurrentFoodViews", allCurrentFoodViews);
        model.addAttribute("foodViewCount", allCurrentFoodViews.size());

        model.addAttribute("totalViewSize", allCurrentDrinkViews.size() + allCurrentFoodViews.size());

        String totalOrderPrice = userService.totalCurrentPrice(username);
        model.addAttribute("totalOrderPrice", totalOrderPrice);

        List<DrinkView> allDrinksView = orderServiceImpl.getAllDrinksView();
        model.addAttribute("allDrinksView", allDrinksView);

        List<FoodView> allFoodsView = orderServiceImpl.getAllFoodsView();
        model.addAttribute("allFoodsView", allFoodsView);

        return "menu-view";
    }

    @DeleteMapping("/drink/{id}")
    public String deleteDrinkFromOrder(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        userService.deleteDrinkFromLastOrder(authentication.getName(), id);

        return "redirect:/order/menu";
    }

    @GetMapping("/drink/{id}")
    public String addDrinkToOrder(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        userService.addDrinkToLastOrder(authentication.getName(), id);

        return "redirect:/order/menu";
    }

    @GetMapping("/food/{id}")
    public String addFoodToOrder(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        userService.addFoodToLastOrder(authentication.getName(), id);

        return "redirect:/order/menu";
    }

    @DeleteMapping("/food/{id}")
    public String deleteFoodFromOrder(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        userService.deleteFoodFromLastOrder(authentication.getName(), id);

        return "redirect:/order/menu";
    }

    @GetMapping("/add/drink")
    String addDrink() {

        return "add-drink";
    }

    @PostMapping("/add/drink")
    String addNewDrink(@Valid DrinkDTO drinkDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        boolean alreadyAdded = drinkServiceImpl.isDrinkAlreadyAdded(drinkDTO);

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

        this.orderServiceImpl.addDrink(drinkDTO, drinkDTO.isAddToMenu());
        orderServiceImpl.clearDrinksCache();

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

        boolean alreadyAdded = foodServiceImpl.isFoodAlreadyAdded(foodDTO);

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

        this.orderServiceImpl.addFood(foodDTO, foodDTO.isAddToMenu());
        orderServiceImpl.clearFoodsCache();

        infoMessage = "Храната беше добавена успешно!";
        redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
        return "redirect:/home";
    }


    @PostMapping("/new")
    public String newOrder(RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userService.addNewOrder(username);

        String infoMessage = "Поръчката беше направена успешно!";
        redirectAttributes.addFlashAttribute("infoMessage", infoMessage);

        return "redirect:/home";
    }

    @GetMapping("/history")
    public String ordersHistory(Model model, RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean isAdmin = userService.isAdmin(username);

        boolean userHasOrders = userService.checkIfUserHasOrders(username);

        if (!userHasOrders && !isAdmin) {
            String infoMessage = "Нямате направени поръчки!";
            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            return "redirect:/home";
        }

        List<OrderView> filteredOrdersWithZeroAmount = userService.getAllCurrentOrdersViews(username);

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