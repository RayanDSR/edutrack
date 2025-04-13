package com.edutrack.edutrack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String SayHello() {
        return "Hello, EduTrack!";
    }
}
