package com.rms.web;

import com.rms.interceptors.MaintenanceInterceptor;
import com.rms.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    private final MaintenanceInterceptor maintenanceInterceptor;
    private final UserService userService;

    public MaintenanceController(MaintenanceInterceptor maintenanceInterceptor, UserService userService) {
        this.maintenanceInterceptor = maintenanceInterceptor;
        this.userService = userService;
    }

    @GetMapping("/")
    public String maintenancePage(Authentication authentication, RedirectAttributes redirectAttributes) {

        boolean isAdmin = userService.isAdmin(authentication.getName());

//        for (GrantedAuthority authority : authentication.getAuthorities()) {
//            String role = authority.getAuthority();
//            if ("ROLE_ADMIN".equals(role)) {
//                isAdmin = true;
//            }
//        }

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

    @GetMapping("/start")
    public String startMaintenance() {
        maintenanceInterceptor.MaintenanceModeStart();
        return "redirect:/";
    }

    @GetMapping("/stop")
    public String stopMaintenance() {
        maintenanceInterceptor.MaintenanceModeStop();
        return "redirect:/";
    }
}
