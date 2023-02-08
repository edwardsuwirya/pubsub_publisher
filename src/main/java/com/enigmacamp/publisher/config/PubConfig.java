package com.enigmacamp.publisher.config;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import com.google.common.primitives.Bytes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageHandler;


@Configuration
public class PubConfig {
    @Bean(name = "inputChannel")
    public PublishSubscribeChannel inputChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean(name = "transformChannel")
    @BridgeTo("pubsubChannel")
    public DirectChannel transformChannel() {
        return new DirectChannel();
    }

    @Bean(name = "pubsubChannel")
    public DirectChannel pubsubChannel() {
        return new DirectChannel();
    }

    @Bean(name = "replyChannel")
    public DirectChannel replyChannel() {
        return new DirectChannel();
    }

    @Transformer(inputChannel = "inputChannel", outputChannel = "transformChannel")
    public String transform(String data) {
        System.out.println("data_" + data);
        return data.toUpperCase();
    }

    @ServiceActivator(inputChannel = "inputChannel")
    public void messageLogSender(String msg) {
        System.out.println("Log=" + msg);
    }

    @Bean
    @ServiceActivator(inputChannel = "pubsubChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "send-email");
    }

//    @ServiceActivator(inputChannel = "replyChannel")
//    public void reply(String res) {
//        System.out.println("???" + res.toString());
//    }
}
