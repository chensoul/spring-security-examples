package com.chensoul.security.globalexceptionhandler;

import static org.hamcrest.Matchers.is;

import com.chensoul.security.globalexceptionhandler.controller.LoginController;
import com.chensoul.security.globalexceptionhandler.handler.RestError;
import com.chensoul.security.globalexceptionhandler.security.CustomAuthenticationEntryPoint;
import com.chensoul.security.globalexceptionhandler.security.CustomSecurityConfig;
import com.chensoul.security.globalexceptionhandler.security.DelegatedAuthenticationEntryPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = CustomSecurityConfig.class)
@Import({LoginController.class, CustomAuthenticationEntryPoint.class, DelegatedAuthenticationEntryPoint.class})
public class SecurityConfigUnitTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	public void whenUserAccessLogin_shouldSucceed() throws Exception {
		mvc.perform(formLogin("/login").user("username", "admin")
				.password("password", "password")
				.acceptMediaType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void whenUserAccessWithWrongCredentialsWithDelegatedEntryPoint_shouldFail() throws Exception {
		RestError re = new RestError(HttpStatus.UNAUTHORIZED.toString(), "Authentication failed");
		mvc.perform(formLogin("/login").user("username", "admin")
				.password("password", "wrong")
				.acceptMediaType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.errorMessage", is(re.getErrorMessage())));
	}

}
