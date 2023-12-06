package com.rms.web;

import com.rms.model.views.LogView;
import com.rms.model.views.OrderView;
import com.rms.service.AuthenticationLogService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/logs")
public class AothenticationLogController {

    private final AuthenticationLogService authenticationLogService;

    public AothenticationLogController(AuthenticationLogService authenticationLogService) {
        this.authenticationLogService = authenticationLogService;
    }

    @GetMapping("/all")
    public String logHistory(Model model, RedirectAttributes redirectAttributes) {

        List<LogView> allLogsOrderedByTime = authenticationLogService.getAllLogs();

        model.addAttribute("allLogsOrderedByTime", allLogsOrderedByTime);

        return "logs";
    }


}
