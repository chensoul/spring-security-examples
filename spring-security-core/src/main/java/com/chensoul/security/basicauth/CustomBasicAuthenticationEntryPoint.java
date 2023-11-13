package com.chensoul.security.basicauth;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Component
public class CustomBasicAuthenticationEntryPoint
        extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonPayload = "{\"message\": \"%s\", \"timestamp\": \"%s\"}";
        String currentTime = java.util.Calendar.getInstance().getTime().toString();
        String formattedPayload = String.format(jsonPayload, e.getMessage(), currentTime);

        response.getOutputStream().write(formattedPayload.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("basic-auth");
        super.afterPropertiesSet();
    }
}
