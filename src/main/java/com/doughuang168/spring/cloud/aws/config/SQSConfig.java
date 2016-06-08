package com.doughuang168.spring.cloud.aws.config;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SQSConfig {

    @Value("${queue.endpoint}")
    private String endpoint;

    @Value("${queue.name}")
    private String queueName;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Bean
    public AmazonSQSClient createSQSClient() {

        AmazonSQSClient amazonSQSClient = new AmazonSQSClient(new BasicAWSCredentials(accessKey,secretKey));
        amazonSQSClient.setEndpoint(endpoint);


        return amazonSQSClient;
    }
}