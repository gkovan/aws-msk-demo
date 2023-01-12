package com.gk.aws.msk.demo.pipe.task;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.aws.msk.demo.pipe.model.KafkaBody;

@Service
public class JsonService {

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
    
}
