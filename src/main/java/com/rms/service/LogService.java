package com.rms.service;

import com.rms.model.entity.LogEntity;
import com.rms.model.views.LogView;

import java.util.List;

public interface LogService {
    void saveLog(LogEntity logEntity);

    List<LogView> getAllLogs();
}
