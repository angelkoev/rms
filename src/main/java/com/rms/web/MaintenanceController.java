//package com.rms.web;
//
//import com.rms.interceptors.MaintenanceInterceptor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@RequestMapping("/maintenance")
//public class MaintenanceController {
//
//    private final MaintenanceInterceptor maintenanceInterceptor;
//
//    public MaintenanceController(MaintenanceInterceptor maintenanceInterceptor) {
//        this.maintenanceInterceptor = maintenanceInterceptor;
//    }
//
//    @GetMapping("/")
//    public String maintenancePage(Authentication authentication, RedirectAttributes redirectAttributes) {
//        boolean isAdmin = false;
//        for (GrantedAuthority authority : authentication.getAuthorities()) {
//            String role = authority.getAuthority();
//            if ("ROLE_ADMIN".equals(role)) {
//                isAdmin = true;
//            }
//        }
//
//        String infoMessage = "";
//        if (isAdmin) {
//            infoMessage = "В момента сайта се намира в maintenance режим!";
//            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
//            return "redirect:/home";
//        }
//
//        return "redirect:maintenance";
//    }
//
//    @GetMapping("/start")
//    public String startMaintenance() {
//        maintenanceInterceptor.activateMaintenanceMode();
//        return "redirect:maintenance";
//    }
//
//    @GetMapping("/stop")
//    public String stopMaintenance() {
//        maintenanceInterceptor.deactivateMaintenanceMode();
//        return "redirect:maintenance";
//    }
//}
