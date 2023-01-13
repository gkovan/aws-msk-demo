package com.gk.aws.msk.demo.pipe.task;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.aws.msk.demo.pipe.model.KafkaBody;
import com.gk.aws.msk.demo.pipe.model.twitter.TwitterResponse;

@Service
public class JsonTask {

    public KafkaBody convertJsonStringToObject(String kafkaBodyString) {
        KafkaBody kafkaBody = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
           kafkaBody = objectMapper.readValue(kafkaBodyString, KafkaBody.class);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }
        return kafkaBody;
    }

    public TwitterResponse convertTwitterResponseStringToObject(String twitterResponseString) {
        ObjectMapper objectMapper = new ObjectMapper();
        TwitterResponse twitterResponse = null;
        try {
            twitterResponse = objectMapper.readValue(twitterResponseString, TwitterResponse.class);
        } catch (JsonProcessingException jpe) {
            // TODO Auto-generated catch block
            jpe.printStackTrace();
        }
        return twitterResponse;
    }
    
}
