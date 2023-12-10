package com.rms.events;

import com.rms.model.entity.LogEntity;
import com.rms.service.Impl.LogServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogoutEventListener implements ApplicationListener<LogoutSuccessEvent> {

    private final LogServiceImpl logServiceImpl;

    public LogoutEventListener(LogServiceImpl logServiceImpl) {
        this.logServiceImpl = logServiceImpl;
    }

    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        Authentication authentication = event.getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            LogEntity logEntity = new LogEntity();
            logEntity.setUsername(authentication.getName());
            logEntity.setTimestamp(LocalDateTime.now());
            logEntity.setStatus("LOGOUT");
            logServiceImpl.saveLog(logEntity);
        }
    }
}
