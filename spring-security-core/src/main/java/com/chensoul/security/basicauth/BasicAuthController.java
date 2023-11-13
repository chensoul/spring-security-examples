package com.chensoul.security.basicauth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicAuthController {

	@GetMapping("/")
	public String home() {
		return "Hello World !!";
	}

	@GetMapping("/public")
	public String publicResource() {
		return "Access allowed to all !!";
	}
}
