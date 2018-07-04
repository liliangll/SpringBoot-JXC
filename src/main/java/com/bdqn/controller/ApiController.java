package com.bdqn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bdqn.component.RoncooJavaMailComponent;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private RoncooJavaMailComponent component;

	@RequestMapping(value = "/mail")
	public String mail(String email) {
		component.sendMail(email);
		return "success";
	}



}
