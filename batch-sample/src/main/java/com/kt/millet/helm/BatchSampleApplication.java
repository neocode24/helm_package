package com.kt.millet.helm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@EnableEncryptableProperties
@EnableBatchProcessing
@SpringBootApplication
public class BatchSampleApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(BatchSampleApplication.class, 
				ArrayUtils.add(args, "requestDate=" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	}
}
