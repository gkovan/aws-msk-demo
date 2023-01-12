package com.gk.aws.msk.demo.pipe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

@Service
public class TranslateTextService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslateTextService.class);

    private static final String REGION = "us-east-1";

    public String translateText(String sourceText, String sourceLang, String targetLang) {
                // Create credentials using a provider chain. For more information, see
        // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
        AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();
        
        AmazonTranslate translate = AmazonTranslateClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds.getCredentials()))
                .withRegion(REGION)
                .build();
 
        TranslateTextRequest request = new TranslateTextRequest()
                .withText(sourceText)
                .withSourceLanguageCode(sourceLang)
                .withTargetLanguageCode(targetLang);
        TranslateTextResult result  = translate.translateText(request);
        LOGGER.info("Translate: " + sourceText + " to: " + result.getTranslatedText());
        return result.getTranslatedText();
    }
    
}
