package com.rms.web;

import com.rms.model.dto.LoginDTO;
import com.rms.model.dto.RegisterDTO;
import com.rms.model.entity.UserEntity;
import com.rms.model.entity.UserRoleEntity;
import com.rms.model.views.FoodView;
import com.rms.model.views.OrderView;
import com.rms.model.views.UserView;
import com.rms.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Stream;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/login")
    public String showLoginForm() {

        return "auth-login";
    }

    @GetMapping("/login-error")
    public String onFailure(Model model) {

        model.addAttribute("errorMessage", "Invalid username or password");

//        this.userService.login(loginDTO.getUsername());
        return "auth-login";
    }

    @GetMapping("/register")
    String register() {

        return "auth-register";
    }

    @PostMapping("/register")
    String registerNewUser(@Valid RegisterDTO registerDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        boolean equalPasswords = registerDTO.getPassword().equals(registerDTO.getConfirmPassword());

        if (!equalPasswords) {
            bindingResult.addError(new FieldError("registerDTO", "confirmPassword", "Паролите НЕ съвпадат !!!"));
        }

        if (bindingResult.hasErrors() || !equalPasswords) {
            redirectAttributes.addFlashAttribute("registerDTO", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);

            return "redirect:/users/register";
        }

        this.userService.register(registerDTO);

//        String infoMessage = "Регистрацията беше успешна! Вече можете да влезете в акаунта си";
//        redirectAttributes.addFlashAttribute("infoMessage", infoMessage);

        return "redirect:/home";
    }

    @GetMapping("/all")
    String showAll(Model model) {

        List<UserView> allUserViews = userService.getAllUserViews();
        model.addAttribute("allUserViews", allUserViews);

        return "allUsers";
    }

    @PostMapping("/addAdmin/{id}")
    public String addAdmin(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {

        userService.addAdmin(userDetails, id);

        List<UserView> allUserViews = userService.getAllUserViews();
        model.addAttribute("allUserViews", allUserViews);

        return "allUsers";
    }

    @PostMapping("/removeAdmin/{id}")
    public String removeAdmin(@PathVariable Long id, Model model) {

        userService.removeAdmin(id);

        List<UserView> allUserViews = userService.getAllUserViews();
        model.addAttribute("allUserViews", allUserViews);

        return "allUsers";
    }

    @ModelAttribute
    public LoginDTO loginDTO() {
        return new LoginDTO();
    }

    @ModelAttribute
    public RegisterDTO registerDTO() {
        return new RegisterDTO();
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("validCredentials");
    }
}