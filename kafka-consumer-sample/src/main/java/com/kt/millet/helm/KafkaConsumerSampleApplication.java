package com.kt.millet.helm;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class KafkaConsumerSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerSampleApplication.class, args);
	}

	

	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {

		return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(3 * 1000))
				.setReadTimeout(Duration.ofSeconds(30 * 1000))
				.build();
	}
}
