package com.kt.millet.helm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.millet.helm.producer.Sender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/field")
public class SenderController {


	@Autowired
	Sender sender;


	@PostMapping("/history")
	public void createHistory(@Valid @RequestBody String dataString) {
		log.debug("message.. : " + dataString);

		sender.send(dataString);
	}
}
