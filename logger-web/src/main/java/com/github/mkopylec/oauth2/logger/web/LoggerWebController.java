package com.github.mkopylec.oauth2.logger.web;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import static java.util.Comparator.comparing;

@Controller
@RequestMapping("/logger")
public class LoggerWebController {

    private List<RequestData> logs = new CopyOnWriteArrayList<>();

    @GetMapping
    public ModelAndView showLoggerPage() {
        logs.sort(comparing(RequestData::getTime));
        return new LoggerPage()
                .setLogs(logs);
    }

    @PostMapping
    public ModelAndView manageLogs(@RequestParam("operation") String operation) {
        if (operation.equals("clear")) {
            logs.clear();
        }
        return showLoggerPage();
    }

    @ResponseBody
    @PutMapping("/request-log")
    public void addRequestLog(@RequestBody RequestData requestData) {
        logs.add(requestData);
    }
}
