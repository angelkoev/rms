package com.rms.web;

import com.rms.model.dto.LoginDTO;
import com.rms.model.dto.RegisterDTO;
import com.rms.model.views.UserView;
import com.rms.service.Impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;

    }

    @GetMapping("/login")
    public String showLoginForm() {

        return "auth-login";
    }

    @GetMapping("/login-error")
    public String onFailure(Model model) {

        model.addAttribute("errorMessage", "Invalid username or password");

        return "auth-login";
    }

    @GetMapping("/register")
    public String register() {

        return "auth-register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid RegisterDTO registerDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        boolean equalPasswords = registerDTO.getPassword().equals(registerDTO.getConfirmPassword());

        if (!equalPasswords) {
            bindingResult.addError(new FieldError("registerDTO", "confirmPassword", "Паролите НЕ съвпадат !!!"));
        }

        if (bindingResult.hasErrors() || !equalPasswords) {
            redirectAttributes.addFlashAttribute("registerDTO", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);

            return "redirect:/users/register";
        }

        this.userServiceImpl.register(registerDTO);

        return "redirect:/home";
    }

    @GetMapping("/all")
    public String showAll(Model model) {

        List<UserView> allUserViews = userServiceImpl.getAllUserViews();
        model.addAttribute("allUserViews", allUserViews);

        return "allUsers";
    }

    @PostMapping("/addAdmin/{id}")
    public String addAdmin(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {

        userServiceImpl.addAdmin(userDetails, id);

        List<UserView> allUserViews = userServiceImpl.getAllUserViews();
        model.addAttribute("allUserViews", allUserViews);

        return "allUsers";
    }

    @PostMapping("/removeAdmin/{id}")
    public String removeAdmin(@PathVariable Long id, Model model) {

        userServiceImpl.removeAdmin(id);

        List<UserView> allUserViews = userServiceImpl.getAllUserViews();
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