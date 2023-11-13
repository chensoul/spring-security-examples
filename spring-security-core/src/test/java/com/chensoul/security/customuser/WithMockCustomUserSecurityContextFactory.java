package com.chensoul.security.customuser;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser mockCustomUser) {
		String username = mockCustomUser.email();
		// a stub CustomUserRepository that returns the user defined in the annotation
		CustomUserRepository userRepository = (email) -> new CustomUser(mockCustomUser.id(), username, "");
		// CustomUserRepositoryUserDetailsService ensures our UserDetails is consistent
		// with our production application
		CustomUserRepositoryUserDetailsService userDetailsService = new CustomUserRepositoryUserDetailsService(
				userRepository);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails,
				userDetails.getPassword(), userDetails.getAuthorities()));
		return securityContext;
	}

}
