//package com.rms.events;
//
//import org.springframework.context.ApplicationEvent;
//
//import java.time.Clock;
//
//public class ExceptionEvent extends ApplicationEvent {
//
//    private String methodName;
//    private String className;
//    private String stackTrace;
//
//    public ExceptionEvent(Object source, String methodName, String className, String stackTrace) {
//        super(source);
//        this.methodName = methodName;
//        this.className = className;
//        this.stackTrace = stackTrace;
//    }
//
//    public String getMethodName() {
//        return methodName;
//    }
//
//    public void setMethodName(String methodName) {
//        this.methodName = methodName;
//    }
//
//    public String getClassName() {
//        return className;
//    }
//
//    public void setClassName(String className) {
//        this.className = className;
//    }
//
//    public String getStackTrace() {
//        return stackTrace;
//    }
//
//    public void setStackTrace(String stackTrace) {
//        this.stackTrace = stackTrace;
//    }
//}
