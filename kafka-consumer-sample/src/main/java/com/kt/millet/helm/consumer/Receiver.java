package com.kt.millet.helm.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Receiver {

	@Autowired
    RestTemplate restTemplate;


    @KafkaListener(topics = "${spring.kafka.topic}")
    public void listen(@Payload String message) {
        log.debug("received message='{}'", message);
    }
}
