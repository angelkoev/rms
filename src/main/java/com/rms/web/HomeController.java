package com.rms.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {

//        model.addAttribute("isLogged", userDetails != null);
//
//        if (userDetails != null) {
//            model.addAttribute("isLogged", userDetails.get);
//        }

        return "index";
    }

}
