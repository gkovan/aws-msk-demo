package com.gk.aws.msk.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class KafkaConfiguration {

    private String bootstrapServers;
    private String applicationId;
    private String inputTopic;
    private String outputTopic;
    private SaslJaasConfig saslJaasConfig;

    @Autowired
    public void setSaslJaasConfig(SaslJaasConfig saslJaasConfig) {
        this.saslJaasConfig = saslJaasConfig;
    }
    
    @Getter
    @Setter
    public static class Security {
        private String securityProtocol;
        private String saslMechanism;
        private String sslProtocol;
        private String sslEnabledProtocolString;
        private String sslEndpointIdentificationAlgorithm;
        private String saslJaasConfig;
    }
}
