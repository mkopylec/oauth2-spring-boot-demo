package com.github.mkopylec.oauth2.logger.web;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

public class LoggerPage extends ModelAndView {

    public LoggerPage() {
        super("logger");
    }

    public LoggerPage setLogs(List<RequestData> logs) {
        set("logs", logs);
        return this;
    }

    private void set(String attribute, Object value) {
        getModel().put(attribute, value);
    }
}
