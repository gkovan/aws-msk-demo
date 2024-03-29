package com.gk.aws.msk.demo.pipe.service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.aws.msk.demo.config.KafkaConfiguration;
import com.gk.aws.msk.demo.pipe.model.PipeProducerRequest;
import com.gk.aws.msk.demo.pipe.model.PipeProducerResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

    private Producer<String, String> kafkaProducer = null;
    private Properties props = new Properties();
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ProducerService(KafkaConfiguration kafkaConfiguration) {
        this.kafkaConfiguration = kafkaConfiguration;

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getBootstrapServers());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");

        kafkaProducer = new KafkaProducer<>(props);
    }

    private KafkaConfiguration kafkaConfiguration;

    public PipeProducerResponse produceRecordSynchronous(PipeProducerRequest request) {
        PipeProducerResponse response = new PipeProducerResponse();

        String requestKafkaBodyAsString = null;
        try {
            requestKafkaBodyAsString = objectMapper.writeValueAsString(request.getKafkaBody());
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }

        ProducerRecord<String, String> record = new ProducerRecord<String, String>(kafkaConfiguration.getInputTopic(),
                request.getKafkaKey(), requestKafkaBodyAsString);
        
        // add kafka header 
        if ( request.getKafkaHeader() != null && request.getKafkaHeader().contains("=")) {
            String[] keyValPair = request.getKafkaHeader().split("=");
            // kafka header added here
            record.headers().add(keyValPair[0], keyValPair[1].getBytes());
        }

        // send the record to the kafka broker
        Future<RecordMetadata> recordMetaData = kafkaProducer.send(record);

        // process the response from the broker
        try {
            RecordMetadata meta = recordMetaData.get();
            response.setStatus("SUCCESS");
            response.setPartition(Integer.toString(meta.partition()));
            response.setOffset(Long.toString(meta.offset()));
            response.setTopic(meta.topic());
            response.setKafkaBody(request.getKafkaBody().getText());
            response.setKafkaKey(request.getKafkaKey());
            response.setKafkaHeader(request.getKafkaHeader());
            response.setSynchronousProducer(request.isSynchronousProducer());
            LOGGER.info("Successfully sent record to topic: " + response.toString());
        } catch (InterruptedException | ExecutionException exception) {
            LOGGER.info("Exception producing a record to topic.");
            exception.printStackTrace();
        }

        return response;
    }

    public PipeProducerResponse produceRecordASynch(PipeProducerRequest request) {
        PipeProducerResponse response = new PipeProducerResponse();

        response.setSynchronousProducer(request.isSynchronousProducer());
        response.setKafkaBody(request.getKafkaBody().getText());
        response.setKafkaKey(request.getKafkaKey());
        response.setKafkaHeader(request.getKafkaHeader());

        String requestKafkaBodyAsString = null;
        try {
            requestKafkaBodyAsString = objectMapper.writeValueAsString(request.getKafkaBody());
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }

        // Create the record to produce
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                kafkaConfiguration.getInputTopic(),
                request.getKafkaKey(), requestKafkaBodyAsString);

        // add kafka header 
        if ( request.getKafkaHeader() != null && request.getKafkaHeader().contains("=")) {
            String[] keyValPair = request.getKafkaHeader().split("=");
            // kafka header added here
            record.headers().add(keyValPair[0], keyValPair[1].getBytes());
        }

        // send the record to the kafka broker asynchronously
        kafkaProducer.send(record, new Callback() {

            @Override
            public void onCompletion(RecordMetadata recordMetaData, Exception e) {
                if (e != null) {
                    LOGGER.info("Exception producing a record to topic.");
                    e.printStackTrace();
                } else {
                    response.setStatus("SUCCESS");
                    response.setPartition(Integer.toString(recordMetaData.partition()));
                    response.setOffset(Long.toString(recordMetaData.offset()));
                    response.setTopic(recordMetaData.topic());             
                    LOGGER.info("Produced record to kafka topic:" + response.toString());
                }
            }

        });

        return response;
    }

}
