//package com.rms.aop;
//
//import com.rms.events.ExceptionEvent;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Aspect
//@Component
//public class ExceptionLoggingAspect {
//
//    @Autowired
//    private ApplicationEventPublisher eventPublisher;
//
//    @AfterThrowing(pointcut = "execution(* com.rms..*.*(..))", throwing = "ex")
//    public void logException(JoinPoint joinPoint, Exception ex) {
//        String methodName = joinPoint.getSignature().getName();
//        String className = joinPoint.getTarget().getClass().getName();
//        String stackTrace = Arrays.toString(ex.getStackTrace());
//
//        eventPublisher.publishEvent(new ExceptionEvent(this, methodName, className, stackTrace));
//    }
//}
