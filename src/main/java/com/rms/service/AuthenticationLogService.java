package com.rms.service;

import com.rms.model.entity.AuthenticationLog;
import com.rms.model.views.LogView;
import com.rms.model.views.ReviewView;
import com.rms.repository.AuthenticationLogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationLogService {

    private final AuthenticationLogRepository authenticationLogRepository;
    private final ModelMapper modelMapper;

    public AuthenticationLogService(AuthenticationLogRepository authenticationLogRepository, ModelMapper modelMapper) {
        this.authenticationLogRepository = authenticationLogRepository;
        this.modelMapper = modelMapper;
    }

    public void saveLog(AuthenticationLog authenticationLog) {
        authenticationLogRepository.save(authenticationLog);
    }

    public List<LogView> getAllLogs() {
        List<AuthenticationLog> authenticationLogByOrderByTimestamp = authenticationLogRepository.getAuthenticationLogByOrderByTimestampDesc();

            List<LogView> Logs = authenticationLogByOrderByTimestamp.stream()
                    .map(authenticationLog -> {
                        LogView logView = modelMapper.map(authenticationLog, LogView.class);

                        String dateTime = authenticationLog.getTimestamp().toString();
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
