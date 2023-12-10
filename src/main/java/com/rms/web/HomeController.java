package com.rms.web;

import com.rms.interceptors.MaintenanceInterceptor;
import com.rms.service.Impl.OrderServiceImpl;
import com.rms.service.Impl.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final MaintenanceInterceptor maintenanceInterceptor;

    private final UserServiceImpl userServiceImpl;
    private final OrderServiceImpl orderServiceImpl;

    public HomeController(MaintenanceInterceptor maintenanceInterceptor, UserServiceImpl userServiceImpl, OrderServiceImpl orderServiceImpl) {
        this.maintenanceInterceptor = maintenanceInterceptor;
        this.userServiceImpl = userServiceImpl;
        this.orderServiceImpl = orderServiceImpl;
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

        boolean isAdmin = userServiceImpl.isAdmin(authentication.getName());

        if (!isAdmin && maintenanceInterceptor.isMaintenanceMode() ) {
            return "maintenance";
        }

        boolean isMenuOK = orderServiceImpl.isMenuOK();

        if (!isMenuOK) {
            orderServiceImpl.getMenu();
            isMenuOK = true;
        }

        return "home";
    }


}