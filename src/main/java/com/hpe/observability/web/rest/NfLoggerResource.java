package com.hpe.observability.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NfLoggerResource {

    private static final Logger log = LoggerFactory.getLogger(NfLoggerResource.class);
    @GetMapping("/write-logs")
    public void generateSampleLogs() {
        log.info("info log");
        log.trace("trace log");
        log.debug("debug log");
        log.warn("warn log");
        log.error("error log");
    }
}
