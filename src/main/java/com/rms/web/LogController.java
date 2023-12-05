//package com.rms.web;
//
//import com.rms.model.dto.FoodDTO;
//import com.rms.model.entity.LogEntity;
//import com.rms.service.LogService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Controller
//@RequestMapping("/logs")
//public class LogController {
//
//    private final LogService logService;
//
//    public LogController(LogService logService) {
//        this.logService = logService;
//    }
//
//
//    @GetMapping( )
//    public String getAllLogs(Model model) {
//
//        List<LogEntity> allLogsOrderedByTimestamp = logService.getAllLogsOrderedByTimestamp();
//
////        LogEntity logEntity = new LogEntity();
////        logEntity.setId(10L);
////        logEntity.setTimestamp(LocalDateTime.now());
////        logEntity.setMethodName("Test Method");
////        logEntity.setClassName("Test Class");
////        logEntity.setStackTrace("Test Stack Trace");
////
////        allLogsOrderedByTimestamp.add(logEntity);
//
//        model.addAttribute("allLogsOrderedByTimestamp", allLogsOrderedByTimestamp);
//
//        return "logs";
//    }
//
//    @ModelAttribute
//    public LogEntity logEntity() {
//        return new LogEntity();
//    }
//}
