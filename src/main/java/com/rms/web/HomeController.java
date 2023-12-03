package com.rms.web;

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


    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {


        if (userDetails != null) {
            return "home";

        }
        return "index";
    }

    @GetMapping("/home")
    public String home() {

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
        if (isAdmin) {
            infoMessage = "В момента сайта се намира в maintenance режим!";
            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            return "redirect:/home";
        }


        return "maintenance";

    }

    @GetMapping("/maintenance/start")
    public String startMaintenance() {
        // FIXME

        return "";
    }

    @GetMapping("/maintenance/stop")
    public String stopMaintenance() {
// FIXME
        return "";
    }

}