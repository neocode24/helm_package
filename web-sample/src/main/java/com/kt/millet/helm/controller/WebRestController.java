package com.kt.millet.helm.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/field")
public class WebRestController {

	

	@Autowired
    RestTemplate restTemplate;
	

	@Value("${target.was.service.name}")
    private String targetWasServiceName;
	
	
	@GetMapping("/grain/{id}")
	public ResponseEntity<String> getGrain(@PathVariable("id") String id) {
		
		log.debug("start [getGrain] ...");
		
		// WAS Service 명칭이 없는 경우, 추가 진행없이 종료함.
		if ( !Optional.ofNullable(targetWasServiceName).filter(f -> !f.isEmpty()).isPresent() ) {
			log.info("No Was Service");
			return new ResponseEntity<String>("No Was Service", HttpStatus.OK);
		}
		
		// WAS Service 호출.
		ResponseEntity<String> response = restTemplate.exchange(
				"http://" + targetWasServiceName + "/field/grain/" + id,
				HttpMethod.GET,
				null,
				String.class
				);
		
		
		if ( !Optional.ofNullable(response.getBody()).isPresent() ) {
			log.debug("No data found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}


	@PostMapping("/grain")
	public @Valid ResponseEntity<String> createGrain(@Valid @RequestBody Map map) {

		log.debug("start [createGrain] ...");

		// Was Service 명칭이 없는 경우, 추가 진행없이 종료함.
		if ( !Optional.ofNullable(targetWasServiceName).filter(f -> !f.isEmpty()).isPresent() ) {
			log.info("No Was Service");
			return new ResponseEntity<>("No Was Service", HttpStatus.OK);
		}

		String response = restTemplate.postForObject(
				"http://" + targetWasServiceName + "/field/grain",
				map,
				String.class
		);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
