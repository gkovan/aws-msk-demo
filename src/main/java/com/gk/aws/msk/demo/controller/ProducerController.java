package com.gk.aws.msk.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gk.aws.msk.demo.config.KafkaConfiguration;

@RestController
public class ProducerController {

    @Autowired
    KafkaConfiguration kafkaConfiguration;

    @GetMapping("/hello")
    public String helloWorld() {
        return "hello";
    }

    @GetMapping("/config")
    public String configuration() {
        return kafkaConfiguration.getSaslJaasConfig().getSaslJaasConfig();
    }
    
}
