package com.hpe.observability.web.rest;

import com.hpe.observability.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NfLoggerResource {

    @Autowired
    private LoggerService service;

    @GetMapping("/write-logs")
    public void generateSampleLogs() {
        service.randomLog();
    }
}
