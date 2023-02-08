package com.enigmacamp.publisher.controller;

import com.enigmacamp.publisher.config.PubSubOutboundGateway;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherController {
    private final PubSubOutboundGateway pubSubOutboundGateway;

    PublisherController(PubSubOutboundGateway pubSubOutboundGateway) {
        this.pubSubOutboundGateway = pubSubOutboundGateway;
    }

    @GetMapping("/send-email")
    public ResponseEntity publish(
            @RequestParam("recipient") String recipient) {
        for (int i = 0; i < 15; i++) {
            Message<String> message = MessageBuilder.withPayload(recipient + "_" + i).build();
            this.pubSubOutboundGateway.sendToPubsub(message);
//            mm.setSuccessCallback((ackId, mes) -> {
//                System.out.println(mes.toString());
//            });
//            System.out.println(recipient + "_" + i + " " + result);
        }


        return ResponseEntity.ok("Messages published asynchronously; status unknown.");
    }
}
