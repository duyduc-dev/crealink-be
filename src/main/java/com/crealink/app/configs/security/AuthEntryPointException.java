package com.crealink.app.configs.security;

import java.io.IOException;
import java.io.Serializable;

import javax.lang.model.type.ErrorType;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.crealink.app.dto.response.ResponseDto;
import com.crealink.app.dto.response.ResponseStatus;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Component
@AllArgsConstructor
public class AuthEntryPointException implements AuthenticationEntryPoint, Serializable {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new ResponseDto<>(ResponseStatus.UNAUTHORIZED)));
    }
}
