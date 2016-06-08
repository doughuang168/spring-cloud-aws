package com.doughuang168.spring.cloud.aws.config;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.*;
import com.doughuang168.spring.cloud.aws.listeners.SQSListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;


@Configuration
public class JMSSQSConfig {

    @Value("${queue.endpoint}")
    private String endpoint;

    @Value("${queue.name}")
    private String queueName;


    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Autowired
    private SQSListener sqsListener;

    @Bean
    public DefaultMessageListenerContainer jmsListenerContainer() {

        SQSConnectionFactory sqsConnectionFactory = SQSConnectionFactory.builder()
                .withAWSCredentialsProvider(new DefaultAWSCredentialsProviderChain())
                .withEndpoint(endpoint)
                .withAWSCredentialsProvider(awsCredentialsProvider)
                .withNumberOfMessagesToPrefetch(10).build();


        DefaultMessageListenerContainer dmlc = new DefaultMessageListenerContainer();
        dmlc.setConnectionFactory(sqsConnectionFactory);
        dmlc.setDestinationName(queueName);

        dmlc.setMessageListener(sqsListener);

        return dmlc;
    }


    @Bean
    public JmsTemplate createJMSTemplate() {

        SQSConnectionFactory sqsConnectionFactory = SQSConnectionFactory.builder()
                .withAWSCredentialsProvider(awsCredentialsProvider)
                .withEndpoint(endpoint)
                .withNumberOfMessagesToPrefetch(10).build();

        JmsTemplate jmsTemplate = new JmsTemplate(sqsConnectionFactory);
        jmsTemplate.setDefaultDestinationName(queueName);
        jmsTemplate.setDeliveryPersistent(false);


        return jmsTemplate;
    }

    private final AWSCredentialsProvider awsCredentialsProvider = new AWSCredentialsProvider() {

        @Override
        public AWSCredentials getCredentials() {
            return new BasicAWSCredentials(accessKey,secretKey);
        }

        @Override
        public void refresh() {

        }
    };

}