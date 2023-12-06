package com.rms.aop;

import com.rms.model.entity.LogEntity;
import com.rms.service.LogService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuthenticationLoggingAspect {

    private final LogService logService;

    public AuthenticationLoggingAspect(LogService logService) {
        this.logService = logService;
    }

    @EventListener
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent successEvent) {
        Authentication authentication = successEvent.getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {

            LogEntity logEntity = new LogEntity();
            logEntity.setUsername(authentication.getName());
            logEntity.setTimestamp(LocalDateTime.now());
            logEntity.setStatus("SUCCESS");
            logService.saveLog(logEntity);
        }
    }
}
