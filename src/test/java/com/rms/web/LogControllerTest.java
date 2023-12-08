package com.rms.web;

import com.rms.model.views.LogView;
import com.rms.service.LogService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LogControllerTest {

    @InjectMocks
    private LogController logController;

    @Mock
    private LogService logServiceMock;

    @Mock
    private Model modelMock;

    @Mock
    private RedirectAttributes redirectAttributesMock;

    @Test
    public void testLogHistory() {
        // Arrange
        List<LogView> fakeLogs = createFakeLogViews();
        when(logServiceMock.getAllLogs()).thenReturn(fakeLogs);

        // Act
        String viewName = logController.logHistory(modelMock, redirectAttributesMock);

        // Assert
        assertEquals("logs", viewName);
        verify(logServiceMock, times(1)).getAllLogs();
        verify(modelMock, times(1)).addAttribute(eq("allLogsOrderedByTime"), eq(fakeLogs));
        verify(redirectAttributesMock, never()).addFlashAttribute(anyString(), any());
    }

    public static List<LogView> createFakeLogViews() {
        List<LogView> fakeLogs = new ArrayList<>();

        fakeLogs.add(createLogView("user1", "2023-12-07", "12:30:45", "Success"));
        fakeLogs.add(createLogView("user2", "2023-12-07", "13:15:20", "Failure"));
        fakeLogs.add(createLogView("user3", "2023-12-08", "09:45:10", "Success"));

        return fakeLogs;
    }

    private static LogView createLogView(String username, String date, String time, String status) {
        LogView logView = new LogView();
        logView.setUsername(username);
        logView.setDate(date);
        logView.setTime(time);
        logView.setStatus(status);
        return logView;
    }
}