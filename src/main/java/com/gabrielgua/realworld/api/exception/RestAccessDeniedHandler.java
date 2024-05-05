package com.gabrielgua.realworld.api.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse httpResponse, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        var status = HttpStatus.FORBIDDEN.value();
        var error = Error.builder()
                .message("Access denied")
                .status("error")
                .build();



        httpResponse.setStatus(status);
        httpResponse.setContentType("application/json");
        
        var out = httpResponse.getOutputStream();
        var mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, error);
        out.flush();
    }
}
