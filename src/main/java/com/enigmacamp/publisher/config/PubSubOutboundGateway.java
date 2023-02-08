package com.enigmacamp.publisher.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway(defaultRequestChannel = "inputChannel")
public interface PubSubOutboundGateway {
    @Gateway(requestChannel = "inputChannel")
    void sendToPubsub(Message<String> text);
}
