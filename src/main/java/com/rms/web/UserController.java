package com.rms.web;

import com.rms.model.dto.LoginDTO;
import com.rms.model.dto.RegisterDTO;
import com.rms.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;


    public UserController(UserServiceImpl userService) {
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


//    @PostMapping("/login")
//    public String onFailure(@Valid LoginDTO loginDTO,
//                     BindingResult bindingResult,
//                     RedirectAttributes redirectAttributes) {
//
//
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginDTO", bindingResult);
//
//            return "redirect:/auth-login";
//        }
//
//        boolean validCredentials = this.userService.checkCredentials(loginDTO.getUsername(), loginDTO.getPassword());
//
//
//        if (!validCredentials) {
//            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
//            bindingResult.addError(new FieldError("loginDTO", "validCredentials", "Incorrect username or password!"));
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginDTO", bindingResult);
////            redirectAttributes.addFlashAttribute("validCredentials", false);
//            return "redirect:/auth-login";
//        }
//
////        this.userService.login(loginDTO.getUsername());
//        return "/home";
//    }


    @GetMapping("/register")
    String register() {

        return "auth-register";
    }

    @PostMapping("/register")
    String registerNewUser(@Valid RegisterDTO registerDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        boolean equalPasswords = registerDTO.getPassword().equals(registerDTO.getConfirmPassword());

        if (!equalPasswords) {
            bindingResult.addError(new FieldError("registerDTO", "confirmPassword", "Passwords NOT match !!!"));
        }

        if (bindingResult.hasErrors() || !equalPasswords) {
            redirectAttributes.addFlashAttribute("registerDTO", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);

            return "redirect:/users/register";
        }

        this.userService.register(registerDTO);
        return "redirect:/home";
    }

//    @GetMapping("/logout")
//    String logout() {
//        if (!this.loggedUser.isLogged()) {
//            return "redirect:/users/login";
//        }
//
//        this.userService.logout();
//        return "redirect:/";
//    }

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