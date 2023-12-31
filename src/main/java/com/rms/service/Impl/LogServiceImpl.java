package com.rms.service.Impl;

import com.rms.model.entity.LogEntity;
import com.rms.model.views.LogView;
import com.rms.repository.LogRepository;
import com.rms.service.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveLog(LogEntity logEntity) {
        logRepository.save(logEntity);
    }
    @Override
    public List<LogView> getAllLogs() {
        List<LogEntity> logEntityByOrderByTimestamp = logRepository.getAuthenticationLogByOrderByTimestampDesc();

            List<LogView> Logs = logEntityByOrderByTimestamp.stream()
                    .map(logEntity -> {
                        LogView logView = modelMapper.map(logEntity, LogView.class);

                        String dateTime = logEntity.getTimestamp().toString();
                        String date = dateTime.split("T")[0];
                        String time = dateTime.split("T")[1].split("\\.")[0];
                        logView.setDate(date);
                        logView.setTime(time);

                        return logView;

                    })
                    .collect(Collectors.toList());

        return Logs;
    }
}
