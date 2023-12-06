package com.rms.web;

import com.rms.interceptors.NightModeInterceptor;
import com.rms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nightmode")
public class NightModeController {

    private final NightModeInterceptor nightModeInterceptor;

    public NightModeController(NightModeInterceptor nightModeInterceptor, UserService userService) {
        this.nightModeInterceptor = nightModeInterceptor;
    }

    @GetMapping("/start")
    public String startNightMode() {
        nightModeInterceptor.NightModeStart();
        return "redirect:/";
    }

    @GetMapping("/stop")
    public String stopNightMode() {
        nightModeInterceptor.NightModeStop();
        return "redirect:/";
    }
}
