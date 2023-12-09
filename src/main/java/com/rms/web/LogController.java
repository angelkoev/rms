package com.rms.web;

import com.rms.model.views.LogView;
import com.rms.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/all")
    public String logHistory(Model model) {

        List<LogView> allLogsOrderedByTime = logService.getAllLogs();

        model.addAttribute("allLogsOrderedByTime", allLogsOrderedByTime);

        return "logs";
    }


}
