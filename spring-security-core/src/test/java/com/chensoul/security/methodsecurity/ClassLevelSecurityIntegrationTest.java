package com.chensoul.security.methodsecurity;

import com.chensoul.security.methodsecurity.service.SystemService;
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
public class ClassLevelSecurityIntegrationTest {

    @Autowired
	SystemService systemService;

    @Configuration
    @ComponentScan("com.chensoul.security.methodsecurity.*")
    public static class SpringConfig {

    }

    @Test
    @WithMockUser(username="john",roles={"ADMIN"})
    public void givenRoleAdmin_whenCallGetSystemYear_return2017(){
        String systemYear = systemService.getSystemYear();
        assertEquals("2017",systemYear);
    }

    @Test
    @WithMockUser(username="john",roles={"VIEWER"})
    public void givenRoleViewer_whenCallGetSystemYear_returnAccessDenied(){
        String systemYear = systemService.getSystemYear();
        assertEquals("2017",systemYear);
    }

    @Test
    @WithMockUser(username="john",roles={"ADMIN"})
    public void givenRoleAdmin_whenCallGetSystemDate_returnDate(){
        String systemYear = systemService.getSystemDate();
        assertEquals("31-12-2017",systemYear);
    }
}
