package com.fmi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fmi.common.error.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType("application/json");
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Unauthorized", e);
        apiError.setTimestamp(null);
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        httpServletResponse.getWriter().print(objectWriter.writeValueAsString(apiError));
    }
}
