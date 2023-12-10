package com.rms.service;

import com.rms.model.entity.LogEntity;
import com.rms.model.views.LogView;
import com.rms.repository.LogRepository;
import com.rms.service.Impl.LogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LogServiceImplTest {
    @Mock
    private LogRepository logRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LogServiceImpl logServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveLog() {
        // Arrange
        LogEntity logEntity = new LogEntity();
        logEntity.setUsername("testUser");
        logEntity.setTimestamp(LocalDateTime.now());
        logEntity.setStatus("SUCCESS");

        // Act
        logServiceImpl.saveLog(logEntity);

        // Assert
        verify(logRepository, times(1)).save(logEntity);
    }

    @Test
    public void testGetAllLogs() {
        // Arrange
        LogEntity logEntity1 = new LogEntity();
        logEntity1.setUsername("user1");
        logEntity1.setTimestamp(LocalDateTime.now());
        logEntity1.setStatus("SUCCESS");

        LogEntity logEntity2 = new LogEntity();
        logEntity2.setUsername("user2");
        logEntity2.setTimestamp(LocalDateTime.now().minusHours(1));
        logEntity2.setStatus("FAILURE");

        List<LogEntity> logEntities = Arrays.asList(logEntity1, logEntity2);

        when(logRepository.getAuthenticationLogByOrderByTimestampDesc()).thenReturn(logEntities);

        LogView logView1 = new LogView();
        logView1.setUsername("user1");
        logView1.setDate("2023-01-01");
        logView1.setTime("12:34:56");
        logView1.setStatus("SUCCESS");

        LogView logView2 = new LogView();
        logView2.setUsername("user2");
        logView2.setDate("2023-01-01");
        logView2.setTime("11:34:56");
        logView2.setStatus("FAILURE");

        when(modelMapper.map(logEntity1, LogView.class)).thenReturn(logView1);
        when(modelMapper.map(logEntity2, LogView.class)).thenReturn(logView2);

        // Act
        List<LogView> result = logServiceImpl.getAllLogs();

        // Assert
        assertEquals(2, result.size());
        assertEquals(logView1, result.get(0));
        assertEquals(logView2, result.get(1));
    }

}