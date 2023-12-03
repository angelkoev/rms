package com.rms.interceptors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.util.Locale;
import java.util.Map;

@Component
public class MaintenanceInterceptor implements HandlerInterceptor {

    private boolean maintenanceMode = false;
    private ThymeleafViewResolver tlvr;

    public MaintenanceInterceptor(ThymeleafViewResolver tlvr) {
        this.tlvr = tlvr;
    }

    public void activateMaintenanceMode() {
        System.out.println("MAINTENANCE ON");
        maintenanceMode = true;
    }

    public void deactivateMaintenanceMode() {
        System.out.println("MAINTENANCE OFF");
        maintenanceMode = false;
    }

    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if (maintenanceMode) {
                boolean hasAdminRole = false;



                if (request.getUserPrincipal() != null) {
                    for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
                        if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                            hasAdminRole = true;
                        }
                    }
                    if (hasAdminRole) {
                        return true;  // Allow access for users with ROLE_ADMIN
                    } else {
                        View maintenanceView = tlvr.resolveViewName("maintenance", Locale.getDefault());
                        if (maintenanceView != null) {
                            maintenanceView.render(Map.of(), request, response);
                        }
                        return false;
//                        response.sendRedirect("maintenance");  // Redirect to the maintenance page
//                        return false;  // Deny access for other roles or both roles
//                        if (!request.getRequestURI().equals("/maintenance.html")) {
//                            // Redirect to maintenance.html
//                            response.sendRedirect("/maintenance.html");
//                            return false; // Stop further processing
//                        }
                    }
                }


            }
            return true;  // Maintenance mode is not active, allow access
        }


}
