//package com.rms.events;
//
//import com.rms.model.entity.LogEntity;
//import com.rms.repositiry.LogRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//public class ExceptionEventListener implements ApplicationListener<ExceptionEvent> {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private final LogRepository logRepository;
//
//    public ExceptionEventListener(LogRepository logRepository) {
//        this.logRepository = logRepository;
//    }
//
//    @Override
//    public void onApplicationEvent(ExceptionEvent event) {
//        logger.error("Exception in method '{}' of class '{}': {}", event.getMethodName(), event.getClassName(), event.getStackTrace());
//
//        LogEntity logEntity = new LogEntity();
//        logEntity.setTimestamp(LocalDateTime.now());
//        logEntity.setMethodName(event.getMethodName());
//        logEntity.setClassName(event.getClassName());
//        logEntity.setStackTrace(event.getStackTrace());
//
//        logRepository.save(logEntity);
//    }
//}