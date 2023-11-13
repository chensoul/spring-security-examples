package com.chensoul.security.formlogin.support;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");

        String jsonPayload = "{\"message\": \"%s\", \"timestamp\": \"%s\"}";
        String currentTime = Calendar.getInstance().getTime().toString();
        String formattedPayload = String.format(jsonPayload, e.getMessage(), currentTime);

        httpServletResponse.getOutputStream().write(formattedPayload.getBytes(StandardCharsets.UTF_8));
    }
}
