package com.cfysu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2019/12/1
 */
@RestController
public class MonitorController {

    @GetMapping("/health")
    public String checkHealth(){
        return "ok";
    }

    @GetMapping("/error")
    public String handleError(){
        return "error";
    }
}
