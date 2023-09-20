package com.spring.ERSBackend.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * custom error mapping
 */
@ComponentScan(basePackageClasses = ErrorController.class)
@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping(value = "/error")
    public String Error() {
        return "uhoh, something went wrong";
    }
}
