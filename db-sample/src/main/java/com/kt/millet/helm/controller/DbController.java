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

import com.kt.millet.helm.bean.GrainBean;
import com.kt.millet.helm.repository.FieldRepository;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/field")
public class DbController {

	

	@Autowired
    RestTemplate restTemplate;
	
	@Autowired
	FieldRepository repository;
	

	@Value("${target.cache.service.name}")
    private String targetCacheServiceName;
	
	
	@GetMapping("/grain/{id}")
	public ResponseEntity<GrainBean> getGrain(@PathVariable("id") long id) {
		
		log.debug("start [getGrain] ...");
		
		// Cache Service 명칭이 없는 경우, Db 조회 함.
		if ( !Optional.ofNullable(targetCacheServiceName).filter(f -> !f.isEmpty()).isPresent() ) {
			log.info("No Cache Service");
						
			// DB에서 조회하여 없는 경우
			Optional<GrainBean> findData = repository.findById(id);
			if ( !findData.isPresent() ) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<>(findData.get(), HttpStatus.OK);
		}
		
		
		// Cache Service 호출.
		ResponseEntity<Map> response = restTemplate.exchange(
				"http://" + targetCacheServiceName + "/field/grain/" + id,
				HttpMethod.GET,
				null,
				Map.class
				);
		
		// Cache에 없으면
		if ( !Optional.ofNullable(response.getBody()).isPresent() ) {
			log.debug("No data found in Cache");
			
			// DB에서도 조회하여 없는 경우
			Optional<GrainBean> findData = repository.findById(id);
			if ( !findData.isPresent() ) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			// cache insert
			restTemplate.postForObject(
					"http://" + targetCacheServiceName + "/field/grain",
					findData.get(),
					Map.class
					);
			
			
			return new ResponseEntity<>(findData.get(), HttpStatus.OK);
		}
		
		// Cache에도 없고, DB에서도 없음.
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/grain")
	public @Valid GrainBean createGrain(@Valid @RequestBody GrainBean bean) {

		log.debug("start [createGrain] ...");

		return repository.save(bean);
	}
}
