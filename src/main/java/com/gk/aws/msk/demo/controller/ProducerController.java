package com.gk.aws.msk.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "hello";
    }
    
}
