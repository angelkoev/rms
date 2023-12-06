package com.rms.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NightModeInterceptor implements HandlerInterceptor {

    private boolean nightMode = false;
    public void NightModeStart() {
        System.out.println("Interceptor for NightMode is activated");
        nightMode = true;
    }

    public void NightModeStop() {
        System.out.println("Interceptor for NightMode is deactivated");
        nightMode = false;
    }

    public boolean isNightMode() {
        return nightMode;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        String nightModeClass = "night-mode-background";

        if (modelAndView != null && isNightMode()) {
            modelAndView.addObject("nightModeClass", nightModeClass);
        }
    }
}
