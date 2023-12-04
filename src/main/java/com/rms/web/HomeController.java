package com.rms.web;

import com.rms.interceptors.MaintenanceInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private final MaintenanceInterceptor maintenanceInterceptor;

    public HomeController(MaintenanceInterceptor maintenanceInterceptor) {
        this.maintenanceInterceptor = maintenanceInterceptor;
    }


    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {


        if (userDetails != null) {
            return "redirect:home";

        }
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {

        if (maintenanceInterceptor.isMaintenanceMode()) {
            return "maintenance";
        }

        return "home";
    }


}