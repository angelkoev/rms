package com.rms.aop;

import com.rms.model.entity.LogEntity;
import com.rms.service.LogService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;

@Aspect
@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@SessionScope
public class AuthenticationLoggingAspect {

    private final LogService logService;
//    private boolean loggingDone = false;

    public AuthenticationLoggingAspect(LogService logService) {
        this.logService = logService;
    }

    private int counter = 0;

    @AfterReturning(pointcut = "execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))", returning = "authentication")
    public void logAfterSuccessfulAuthentication(Authentication authentication) {
        counter++;
        if (counter % 2 == 0 && authentication != null && authentication.isAuthenticated()) {
            LogEntity logEntity = new LogEntity();
            logEntity.setUsername(authentication.getName());
            logEntity.setTimestamp(LocalDateTime.now());
            logEntity.setStatus("SUCCESS");
            logService.saveLog(logEntity);
//            loggingDone = true;
        }
    }

//    @EventListener
//    public void handleAuthenticationSuccess(AuthenticationSuccessEvent successEvent) {
//        Authentication authentication = successEvent.getAuthentication();
//        if (authentication instanceof UsernamePasswordAuthenticationToken) {
//
//            LogEntity logEntity = new LogEntity();
//            logEntity.setUsername(authentication.getName());
//            logEntity.setTimestamp(LocalDateTime.now());
//            logEntity.setStatus("SUCCESS");
//            logService.saveLog(logEntity);
//        }
//    }
}
