package com.gk.aws.msk.demo.pipe.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gk.aws.msk.demo.config.KafkaConfiguration;
import com.gk.aws.msk.demo.pipe.model.KafkaBody;
import com.gk.aws.msk.demo.pipe.model.PipeProducerRequest;
import com.gk.aws.msk.demo.pipe.model.PipeProducerResponse;
import com.gk.aws.msk.demo.pipe.model.twitter.TwitterResponse;
import com.gk.aws.msk.demo.pipe.service.ProducerService;
import com.gk.aws.msk.demo.pipe.service.TwitterService;

@RestController
public class ProducerController {

    KafkaConfiguration kafkaConfiguration;

    private ProducerService producerService;
    private TwitterService twitterService;

    @Autowired
    public ProducerController(KafkaConfiguration kafkaConfiguration, ProducerService producerService, TwitterService twitterService) {
        this.kafkaConfiguration = kafkaConfiguration;
        this.producerService = producerService;
        this.twitterService = twitterService;
    }


    @GetMapping("/hello")
    public String helloWorld() {
        return "hello";
    }

    /*
     * HTTP GET request for testing purposes.  Request is hard coded.
     */
    @GetMapping("/producer")
    public PipeProducerResponse produceRecord() {
        // Hard code a request.
        PipeProducerRequest request = new PipeProducerRequest();
        KafkaBody kafkaBody = new KafkaBody();
        kafkaBody.setText("hello my name is Gerry. This is a synchronous producer.");
        kafkaBody.setSourceLang("en");
        kafkaBody.setTargetLang("fr");
        kafkaBody.setTranslate(true);
        request.setKafkaBody(kafkaBody);
        request.setKafkaHeader("source=ProducerController");
        request.setKafkaKey("en-fr");
        request.setSynchronousProducer(true);

        PipeProducerResponse response = producerService.produceRecordSynchronous(request);
        
        return response;
    }

    @PostMapping("/producer")
    public PipeProducerResponse produceRecord(@RequestBody PipeProducerRequest request) {

        PipeProducerResponse response = producerService.produceRecordSynchronous(request);
        
        return response;
    }

    /*
     * HTTP GET request for testing purposes.  Request is hard coded.
     */
    @GetMapping("/producer-asynch")
    public PipeProducerResponse produceRecordAsync() {
        PipeProducerRequest request = new PipeProducerRequest();
        KafkaBody kafkaBody = new KafkaBody();
        kafkaBody.setText("Hello my name is Gerry.  This is a asynchronous producer.");
        kafkaBody.setSourceLang("en");
        kafkaBody.setTargetLang("fr");
        kafkaBody.setTranslate(false);
        request.setKafkaBody(kafkaBody);
        request.setKafkaHeader("source=ProducerController");
        request.setKafkaKey("en-fr");
        request.setSynchronousProducer(false);
        PipeProducerResponse response = producerService.produceRecordASynch(request);      
        return response;
    }

    @PostMapping("/producer-asynch")
    public PipeProducerResponse produceRecordAsynch(@RequestBody PipeProducerRequest request) {

        PipeProducerResponse response = producerService.produceRecordASynch(request);
        
        return response;
    }

    @GetMapping("/twitter") 
    public String producerTwitter() {

        TwitterResponse twitterResponse = twitterService.getTweets();
        twitterResponse.getData().forEach(dataTweet -> {

            PipeProducerRequest request = new PipeProducerRequest();
            KafkaBody kafkaBody = new KafkaBody();
            kafkaBody.setText(dataTweet.getText());
            kafkaBody.setSourceLang("fr");
            kafkaBody.setTargetLang("en");
            kafkaBody.setTranslate(true);
            request.setKafkaBody(kafkaBody);
            request.setKafkaHeader("source=twitter");
            request.setKafkaKey("fr-en");
            request.setSynchronousProducer(false);
            PipeProducerResponse response = producerService.produceRecordASynch(request); 

            System.out.println(dataTweet.getText());
        });

        return "Success";
    }

    
}
