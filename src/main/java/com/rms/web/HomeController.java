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
            return "home";

        }
        return "index";
    }

    @GetMapping("/home")
    public String home() {

        if (maintenanceInterceptor.isMaintenanceMode()) {
            return "maintenance";
        }

        return "home";
    }

    @GetMapping("/maintenance")
    public String maintenance(Authentication authentication, RedirectAttributes redirectAttributes) {

        boolean isAdmin = false;
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                isAdmin = true;
            }
        }

        String infoMessage = "";
        if (isAdmin && maintenanceInterceptor.isMaintenanceMode()) {
            infoMessage = "В момента сайта се намира в режим на профилактика!";
            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            return "redirect:/home";
        }


        if (maintenanceInterceptor.isMaintenanceMode()) {
            return "maintenance";
        }

        return "home";
    }

    @GetMapping("/maintenance/start")
    public String startMaintenance() {
        maintenanceInterceptor.activateMaintenanceMode();
        return "redirect:/";
    }

    @GetMapping("/maintenance/stop")
    public String stopMaintenance() {
        maintenanceInterceptor.deactivateMaintenanceMode();
        return "redirect:/";
    }

}