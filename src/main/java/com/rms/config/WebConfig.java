package com.rms.config;

import com.rms.interceptors.NightModeInterceptor;
import com.rms.interceptors.MaintenanceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final MaintenanceInterceptor maintenanceInterceptor;
    private final NightModeInterceptor nightModeInterceptor;

    public WebConfig(MaintenanceInterceptor maintenanceInterceptor, NightModeInterceptor nightModeInterceptor) {
        this.maintenanceInterceptor = maintenanceInterceptor;
        this.nightModeInterceptor = nightModeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(maintenanceInterceptor);
        registry.addInterceptor(nightModeInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
