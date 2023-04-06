package com.example.springbootecsloggingexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoggingController {

    private final Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @GetMapping("/")
    public String index() {
        logger.info("index was requested");
        return "<div> Greetings from Spring Boot! </div>\n" +
                "<div><a href=\"/info\">/info</a> <a href=\"/debug\">/debug</a> <a href=\"/exception\">/exception</a>  and <a href=\"/warn\">/warn</a>  log things </div>\n" +
                "<div><a href=\"/info?message=foo\">/info?message=foo</a> </div>\n" +
                "<div><a href=\"/mdc?key=userid&value=42\">/mdc?key=userid&value=42/a></div>\n" ;
    }

    @GetMapping("/debug")
    public String debug(@RequestParam(name = "message", defaultValue = "no message") String message) {
        logger.debug("debug message='{}'", message);
        return "logged a line with level debug and message '"+message+"'";
    }

    @GetMapping("/warn")
    public String warn(@RequestParam(name = "message", defaultValue = "no message") String message) {
        logger.warn("warning message='{}'", message);
        return "logged a line with level warn and message '"+message+"'";
    }

    @GetMapping("/info")
    public String info(@RequestParam(name = "message", defaultValue = "no message") String message) {
        logger.info("info message='{}'", message);
        return "logged a line with level info and message '"+message+"'";
    }

    @GetMapping("/mdc")
    public String info(@RequestParam("key") String key, @RequestParam("value") String value) {
        MDC.put(key, value);
        try {
            logger.info("logging info message with mdc value set");
            return "logging info with mdc value key: '" + key + "' value: '" + value + " '";
        } finally {
            MDC.remove(key);
        }
    }

    @GetMapping("/exception")
    public String exception() {
        try {
            throw new Exception("example Exception");
        } catch(Exception e) {
            logger.error("logging exception stacktrace", e);
        }
        return "logging exception at error level";
    }
}
