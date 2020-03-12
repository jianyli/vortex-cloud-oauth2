package com.vortex.cloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("test")
    public String test() {
        return "test";
    }

    @RequestMapping("hi")
    public String oauth() {
        return "oauth";
    }
}
