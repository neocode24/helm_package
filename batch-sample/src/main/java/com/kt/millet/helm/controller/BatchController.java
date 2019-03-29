package com.kt.millet.helm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kt.millet.helm.bean.BatchBean;
import com.kt.millet.helm.repository.FieldBatchRepository;
import com.kt.millet.helm.repository.FieldRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/field")
public class BatchController {


	@Autowired
    RestTemplate restTemplate;
	
	@Autowired
	FieldRepository fieldRepository;
	
	@Autowired
	FieldBatchRepository batchRepository;
	
	@GetMapping("/result")
	public List<BatchBean> getBatchStatus() {
		
		log.debug("start [getBatchStatus] ...");
		
		List<BatchBean> list = new ArrayList<>();
		batchRepository.findAll().forEach(list::add);
		
		return list;
	}
	
	@PostMapping("/batch")
	public @Valid BatchBean createBatch() {
		
		log.debug("start [createGrain] ...");
		
		long count = StreamSupport.stream(fieldRepository.findAll().spliterator(), false).count();
		
		log.debug("count: " + count);
		
		BatchBean batchBean = BatchBean.builder()
			.count(count)
			.build();
		
		return batchRepository.save(batchBean);
	}
}
