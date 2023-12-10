package com.rms.web;

import com.rms.model.views.LogView;
import com.rms.service.Impl.LogServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/logs")
public class LogController {

    private final LogServiceImpl logServiceImpl;

    public LogController(LogServiceImpl logServiceImpl) {
        this.logServiceImpl = logServiceImpl;
    }

    @GetMapping("/all")
    public String logHistory(Model model) {

        List<LogView> allLogsOrderedByTime = logServiceImpl.getAllLogs();

        model.addAttribute("allLogsOrderedByTime", allLogsOrderedByTime);

        return "logs";
    }


}
