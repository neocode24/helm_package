package com.kt.millet.helm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/session")
public class SessionController {

	@GetMapping("/create")
	public String test(HttpServletRequest request) {
		log.debug("create session");
		return request.getSession(true).getId();
	}
	
	@GetMapping("/remove")
	public boolean remove(HttpSession session) {
		if ( !session.isNew() ) {
			session.invalidate();
			log.debug("remove session");
			return true;
		}
		return false;
	}
}
