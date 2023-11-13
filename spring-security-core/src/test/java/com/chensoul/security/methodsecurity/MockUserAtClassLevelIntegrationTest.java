package com.chensoul.security.methodsecurity;


import com.chensoul.security.methodsecurity.service.UserRoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith({SpringExtension.class})
@ContextConfiguration
@WithMockUser(username = "john", roles = { "VIEWER" })
public class MockUserAtClassLevelIntegrationTest {

    @Test
    public void givenRoleViewer_whenCallGetUsername_thenReturnUsername() {
        String currentUserName = userService.getUsername();
        assertEquals("john", currentUserName);
    }

    @Autowired
	UserRoleService userService;

    @Configuration
    @ComponentScan("com.chensoul.security.methodsecurity.*")
    public static class SpringConfig {

    }
}
