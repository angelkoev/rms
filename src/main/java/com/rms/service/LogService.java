//package com.rms.service;
//
//import com.rms.model.entity.LogEntity;
//import com.rms.repositiry.LogRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class LogService {
//
//    private final LogRepository logRepository;
//
//    public LogService(LogRepository logRepository) {
//        this.logRepository = logRepository;
//    }
//
//    public List<LogEntity> getAllLogsOrderedByTimestamp() {
//        return logRepository.findAllByOrderByTimestampDesc();
//    }
//}
