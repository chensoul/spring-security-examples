package com.chensoul.security.xss;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XssController {

	@GetMapping("/xss")
	public String home(String params) {
		return params;
	}
}
