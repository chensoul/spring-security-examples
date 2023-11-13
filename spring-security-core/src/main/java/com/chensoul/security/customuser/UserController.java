package com.chensoul.security.customuser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for exposing User information.
 *
 * @author Rob Winch
 */
@RestController
public class UserController {

	@GetMapping("/user")
	public CustomUser user(@CurrentUser CustomUser currentUser) {
		return currentUser;
	}

}
