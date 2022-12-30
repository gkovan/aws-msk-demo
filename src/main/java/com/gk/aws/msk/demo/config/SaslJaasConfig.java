package com.gk.aws.msk.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.gk.aws.msk.demo.util.JsonLoader;

@Component
@Profile("secure")
@ConfigurationProperties(prefix="kafka")  // GK not sure if this line is needed
@PropertySource(name= "saslConfig", value="file:${AWS_SECRETS_PATH}/${awsSecrets.mskSecret}", factory=JsonLoader.class)
public class SaslJaasConfig {

    private static final String KAFKA_SCRAM_LOGIN_MODULE_CLASS = "org.apache.kafka.common.security.scram.ScramLoginModule";
    
    @Value("${saslConfig.username}")
    private String username;
    @Value("${saslConfig.password}")
    private String password;


    public String getSaslJaasConfig() {
       return String.format("%s required username=\"%s\" password=\"%s\";", KAFKA_SCRAM_LOGIN_MODULE_CLASS, escapeStringForJaasConfig(username), escapeStringForJaasConfig(password));
    }

    private String escapeStringForJaasConfig(String stringToEscape) {
        return stringToEscape.replace("\"", "\\\"");
    }
}
