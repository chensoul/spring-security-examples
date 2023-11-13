package com.chensoul.security.methodsecurity;

import com.chensoul.security.methodsecurity.entity.CustomUser;
import com.chensoul.security.methodsecurity.service.UserRoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration
public class UserDetailsIntegrationTest {

	@Autowired
	UserRoleService userService;

	@Configuration
	@ComponentScan("com.chensoul.security.methodsecurity.*")
	public static class SpringConfig {

	}

	@Test
	@WithUserDetails(value = "john", userDetailsServiceBeanName = "userDetailService")
	public void whenJohn_callLoadUserDetail_thenOK() {
		CustomUser user = userService.loadUserDetail("jane");
		assertEquals("jane", user.getNickName());
	}

	@Test
	@WithUserDetails(value = "jane", userDetailsServiceBeanName = "userDetailService")
	public void givenJane_callSecuredLoadUserDetailWithJane_thenOK() {
		CustomUser user = userService.securedLoadUserDetail("jane");
		assertEquals("jane", user.getNickName());
		assertEquals("jane", user.getUsername());
	}

	@Test //(expected = AccessDeniedException.class)
	@WithUserDetails(value = "john", userDetailsServiceBeanName = "userDetailService")
	public void givenJohn_callSecuredLoadUserDetailWithJane_thenAccessDenied() {
		userService.securedLoadUserDetail("jane");
	}

	@Test //(expected = AccessDeniedException.class)
	@WithUserDetails(value = "john", userDetailsServiceBeanName = "userDetailService")
	public void givenJohn_callSecuredLoadUserDetailWithJohn_thenAccessDenied() {
		userService.securedLoadUserDetail("john");
	}
}
