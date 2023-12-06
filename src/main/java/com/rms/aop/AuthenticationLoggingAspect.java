package com.rms.aop;

import com.rms.model.entity.AuthenticationLog;
import com.rms.service.AuthenticationLogService;
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

    private final AuthenticationLogService authenticationLogService;

    public AuthenticationLoggingAspect(AuthenticationLogService authenticationLogService) {
        this.authenticationLogService = authenticationLogService;
    }

    @EventListener
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent successEvent) {
        Authentication authentication = successEvent.getAuthentication();
        if (authentication != null && authentication instanceof UsernamePasswordAuthenticationToken) {

            AuthenticationLog authenticationLog = new AuthenticationLog();
            authenticationLog.setUsername(authentication.getName());
            authenticationLog.setTimestamp(LocalDateTime.now());
            authenticationLog.setStatus("SUCCESS");
            authenticationLogService.saveLog(authenticationLog);
        }
    }
}
