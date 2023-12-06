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

    private boolean isMaintenanceMode = false;
    private final ThymeleafViewResolver thymeleafViewResolver;

    public MaintenanceInterceptor(ThymeleafViewResolver thymeleafViewResolver) {
        this.thymeleafViewResolver = thymeleafViewResolver;
    }

    public void MaintenanceModeStart() {
        System.out.println("Interceptor for maintenance is activated");
        isMaintenanceMode = true;
    }

    public void MaintenanceModeStop() {
        System.out.println("Interceptor for maintenance is deactivated");
        isMaintenanceMode = false;
    }

    public boolean isMaintenanceMode() {
        return isMaintenanceMode;
    }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if (isMaintenanceMode) {
                boolean hasAdminRole = false;

                if (request.getUserPrincipal() != null) {
                    for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
                        if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                            hasAdminRole = true;
                        }
                    }
                    if (hasAdminRole) {
                        return true;
                    } else {
                        View maintenanceView = thymeleafViewResolver.resolveViewName("maintenance", Locale.getDefault());
                        if (maintenanceView != null) {
                            maintenanceView.render(Map.of(), request, response);
                        }
                        return false;
                    }
                }


            }
            return true;
        }


}
