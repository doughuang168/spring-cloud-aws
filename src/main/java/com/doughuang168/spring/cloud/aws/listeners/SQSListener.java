package com.doughuang168.spring.cloud.aws.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class SQSListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SQSListener.class);

    public void onMessage(Message message) {

        try {

            // Cast the received message as TextMessage and print the text to screen.
            if (message != null) {
                LOGGER.info("Received message "+ ((TextMessage) message).getText());
                //System.out.println("Received: " + ((TextMessage) message).getText());
            }
        } catch (JMSException e) {
            LOGGER.error("Error processing message ",e);
        }
    }
}

