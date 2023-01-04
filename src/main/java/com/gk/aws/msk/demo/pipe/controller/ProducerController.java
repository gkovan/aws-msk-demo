package com.gk.aws.msk.demo.pipe.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gk.aws.msk.demo.config.KafkaConfiguration;
import com.gk.aws.msk.demo.pipe.model.PipeProducerRequest;
import com.gk.aws.msk.demo.pipe.model.PipeProducerResponse;
import com.gk.aws.msk.demo.pipe.service.ProducerService;

@RestController
public class ProducerController {

    KafkaConfiguration kafkaConfiguration;

    private ProducerService producerService;

    @Autowired
    public ProducerController(KafkaConfiguration kafkaConfiguration, ProducerService producerService) {
        this.kafkaConfiguration = kafkaConfiguration;
        this.producerService = producerService;
    }


    @GetMapping("/hello")
    public String helloWorld() {
        return "hello";
    }

    @GetMapping("/producer")
    public PipeProducerResponse produceRecord() {
        PipeProducerRequest request = new PipeProducerRequest();
        request.setKafkaBody("this is the kafka body" + UUID.randomUUID().toString());
        request.setKafkaHeader("this is the kafka header");
        request.setKafkaKey("this is the kafka key");
        request.setSynchronousProducer(true);

        PipeProducerResponse response = producerService.produceRecordSynchronous(request);
        
        return response;
    }

    @GetMapping("/produce-asynch")
    public PipeProducerResponse produceRecordAsync() {
        PipeProducerRequest request = new PipeProducerRequest();
        request.setKafkaBody("this is the kafka body" + UUID.randomUUID().toString());
        request.setKafkaHeader("this is the kafka header");
        request.setKafkaKey("this is the kafka key");
        request.setSynchronousProducer(false);
        PipeProducerResponse response = producerService.produceRecordASynch(request);      
        return response;
    }

    @PostMapping("/producer")
    public PipeProducerResponse produceRecord(@RequestBody PipeProducerRequest request) {

        PipeProducerResponse response = producerService.produceRecordSynchronous(request);
        
        return response;
    }

    
}
