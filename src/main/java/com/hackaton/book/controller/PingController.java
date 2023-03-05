package com.hackaton.book.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class PingController {
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
