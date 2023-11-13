package com.chensoul.security.denyonmissing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = DenyApplication.class)
public class DenyOnMissingControllerIntegrationTest {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;

	@BeforeAll
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.build();
	}

	@Test
	@WithMockUser(username = "user")
	public void givenANormalUser_whenCallingHello_thenAccessDenied() throws Exception {
		mockMvc.perform(get("/hello"))
			.andExpect(status().isOk())
			.andExpect(content().string("Hello world!"));
	}

	@Test
	@WithMockUser(username = "user")
	public void givenANormalUser_whenCallingBye_thenAccessDenied() throws Exception {
		mockMvc.perform(get("/bye"));
	}
}
