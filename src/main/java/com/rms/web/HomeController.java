package com.rms.web;

import com.rms.interceptors.MaintenanceInterceptor;
import com.rms.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final MaintenanceInterceptor maintenanceInterceptor;

    private final UserService userService;

    public HomeController(MaintenanceInterceptor maintenanceInterceptor, UserService userService) {
        this.maintenanceInterceptor = maintenanceInterceptor;
        this.userService = userService;
    }


    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails) {


        if (userDetails != null) {
            return "redirect:home";

        }
        return "index";
    }

    @GetMapping("/home")
    public String home(Authentication authentication) {

        boolean isAdmin = userService.isAdmin(authentication.getName());

        if (!isAdmin && maintenanceInterceptor.isMaintenanceMode() ) {
            return "maintenance";
        }

        return "home";
    }


}