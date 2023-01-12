package com.gk.aws.msk.demo.pipe.service;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.aws.msk.demo.config.KafkaConfiguration;
import com.gk.aws.msk.demo.pipe.model.KafkaBody;
import com.gk.aws.msk.demo.pipe.task.JsonTask;

@Service
public class PipeStreamService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PipeStreamService.class);

	private KafkaConfiguration kafkaConfiguration;
	private TranslateTextService translateTextService;
	private JsonTask jsonTask;

	private KafkaStreams streams = null;
	private Topology topology = null;
	private final StreamsBuilder builder = new StreamsBuilder();
	private final Properties props = new Properties();
	private ObjectMapper objectMapper = new ObjectMapper();

	private String INPUT_TOPIC;
	private String OUTPUT_TOPIC;

	@Autowired
	public PipeStreamService(KafkaConfiguration kafkaConfiguration, TranslateTextService translateTextService, JsonTask jsonTask) {
		super();
		this.kafkaConfiguration = kafkaConfiguration;
		this.translateTextService = translateTextService;
		this.jsonTask = jsonTask;
		
		INPUT_TOPIC = kafkaConfiguration.getInputTopic();
		OUTPUT_TOPIC = kafkaConfiguration.getOutputTopic();

		props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfiguration.getApplicationId());
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getBootstrapServers());
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		
		// Kafka Stream
		builder.stream(INPUT_TOPIC)
		.mapValues(record -> jsonTask.convertJsonStringToObject((String)record))
		.filter( (key, value) -> value.isTranslate() == true)
		.mapValues((record -> translateTextService.translateText(record.getText(), record.getSourceLang(), record.getTargetLang())))
		.to(OUTPUT_TOPIC);
		
        topology = builder.build();
        
        streams = new KafkaStreams(topology, props);
	}
	
	public Topology getTopology() {
		return topology;
	}
	
	public Properties getProperties() {
		return props;
	}

	public void start() {
		LOGGER.info("Pipe Stream Service started.");
		try {
			streams.start();
		} catch (final Throwable e) {
			LOGGER.error("Error starting the Pipe Stream Service.", e);
		}
	}

	public void stop() {
		LOGGER.info("Pipe Stream Service stopped");
		streams.close();
	}

}
