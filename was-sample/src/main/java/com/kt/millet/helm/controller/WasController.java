package com.kt.millet.helm.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/field")
public class WasController {

	

	@Autowired
    RestTemplate restTemplate;
	

	@Value("${target.db.service.name}")
    private String targetDbServiceName;
	
	@Value("${target.cache.service.name}")
    private String targetCacheServiceName;
	
	
	@GetMapping("/grain/{id}")
	public ResponseEntity<String> getGrain(@PathVariable("id") String id) {
		
		log.debug("start [getGrain] ...");
				
		// Db Service 명칭이 없는 경우, 추가 진행없이 종료함.
		if ( !Optional.ofNullable(targetDbServiceName).filter(f -> !f.isEmpty()).isPresent() ) {
			
			// DB, Cache Service 명칭이 없는 경우
			if ( !Optional.ofNullable(targetCacheServiceName).filter(f -> !f.isEmpty()).isPresent()) {
				log.info("No Db, Cache Service");
				return new ResponseEntity<String>("No Db, Cache Service", HttpStatus.OK);
			} 
			// Cache Service가 있는 경우
			else {
				// Cache Service 호출.
				ResponseEntity<String> response = restTemplate.exchange(
						"http://" + targetCacheServiceName + "/field/grain/" + id,
						HttpMethod.GET,
						null,
						String.class
						);
				
				return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
			}
		}
		
		// DB Service 호출.
		ResponseEntity<String> response = restTemplate.exchange(
				"http://" + targetDbServiceName + "/field/grain/" + id,
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

		// Db Service 명칭이 없는 경우, 추가 진행없이 종료함.
		if ( !Optional.ofNullable(targetDbServiceName).filter(f -> !f.isEmpty()).isPresent() ) {
			
			// DB, Cache Service 명칭이 없는 경우
			if ( !Optional.ofNullable(targetCacheServiceName).filter(f -> !f.isEmpty()).isPresent()) {
				log.info("No Db, Cache Service");
				return new ResponseEntity<String>("No Db, Cache Service", HttpStatus.OK);
			} 
			// Cache Service가 있는 경우
			else {
				// Cache Service 호출
				String response = restTemplate.postForObject(
						"http://" + targetCacheServiceName + "/field/grain",
						map,
						String.class
						);
				
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		// DB Service 호출
		String response = restTemplate.postForObject(
				"http://" + targetDbServiceName + "/field/grain",
				map,
				String.class
		);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
