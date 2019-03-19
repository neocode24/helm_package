package com.kt.millet.helm.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kt.millet.helm.bean.GrainBean;
import com.kt.millet.helm.repository.FieldRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/field")
public class CacheController {

	

	@Autowired
    RestTemplate restTemplate;
	
	@Autowired
	FieldRepository repository;
	
	
	@GetMapping("/grain/{id}")
	public ResponseEntity<GrainBean> getGrain(@PathVariable("id") long id) {
		
		log.debug("start [getGrain] ...");
		
		// Redis에서 조회하여 없는 경우. Redis Cache 에 데이터가 없는 경우는 오류가 아닌 정상 처리.
		Optional<GrainBean> findData = repository.findById(id);
		if ( !findData.isPresent() ) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			log.info("No data found in cache");
			return new ResponseEntity<>(HttpStatus.OK);
		}

		log.info("Data found in cache");
		
		return new ResponseEntity<>(findData.get(), HttpStatus.OK);
	}
	
	@PostMapping("/grain")
	public @Valid GrainBean createGrain(@Valid @RequestBody GrainBean bean) {
		
		log.debug("start [createGrain] ...");
		
		return repository.save(bean);
	}
}
