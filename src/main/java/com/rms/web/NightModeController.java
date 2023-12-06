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
//    private final UserService userService;

    public NightModeController(NightModeInterceptor nightModeInterceptor, UserService userService) {
        this.nightModeInterceptor = nightModeInterceptor;
//        this.userService = userService;
    }

//    @GetMapping("/")
//    public String maintenancePage(Authentication authentication, RedirectAttributes redirectAttributes) {
//
//        boolean isAdmin = userService.isAdmin(authentication.getName());
//
//        String infoMessage = "";
//        if (isAdmin && nightModeInterceptor.isNightMode()) {
//            infoMessage = "В момента сайта се намира в режим на профилактика!";
//            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
//            return "redirect:/home";
//        }
//
//
//        if (nightModeInterceptor.isMaintenanceMode()) {
//            return "maintenance";
//        }

//        return "home";
//    }

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
