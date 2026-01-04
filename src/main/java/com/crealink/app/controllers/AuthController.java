package com.crealink.app.controllers;

import java.time.Duration;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crealink.app.configs.AppConfigValue;
import com.crealink.app.configs.security.AuthSession;
import com.crealink.app.configs.security.AuthUser;
import com.crealink.app.constants.AppPath;
import com.crealink.app.constants.CookiesKey;
import com.crealink.app.constants.HeadersKey;
import com.crealink.app.dto.auth.SignInRequestDto;
import com.crealink.app.dto.auth.SignInResponseDto;
import com.crealink.app.dto.auth.SignupRequestDto;
import com.crealink.app.dto.auth.SignupResponseDto;
import com.crealink.app.dto.response.ResponseDto;
import com.crealink.app.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.AUTH_API)
public class AuthController {

    private final AuthService authService;
    private final AppConfigValue appConfigValue;

    @PostMapping(AppPath.AUTH_SIGNUP)
    public ResponseEntity<ResponseDto<?>> signup(@RequestBody @Valid SignupRequestDto request) {
        SignupResponseDto response = authService.signup(request);
        String jwt = response.accessToken();
        ResponseCookie cookie = ResponseCookie.from(CookiesKey.ACCESS_TOKEN, jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofMillis(appConfigValue.getExpireTime()))
                .build();

        return ResponseEntity.ok()
                .header(HeadersKey.SET_COOKIE, cookie.toString())
                .body(new ResponseDto<>("Signup successful"));
    }
    
    @PostMapping(AppPath.AUTH_LOGIN)
    public ResponseEntity<ResponseDto<?>> login(@RequestBody @Valid SignInRequestDto request) {
            SignInResponseDto response = authService.signIn(request);
            String jwt = response.accessToken();
            ResponseCookie cookie = ResponseCookie.from(CookiesKey.ACCESS_TOKEN, jwt)
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .sameSite("Strict")
                        .maxAge(Duration.ofMillis(appConfigValue.getExpireTime()))
                        .build();

            return ResponseEntity.ok()
                        .header(HeadersKey.SET_COOKIE, cookie.toString())
                        .body(new ResponseDto<>("Login successful"));
    }

    @GetMapping(AppPath.AUTH_ME)
    public ResponseEntity<ResponseDto<?>> getCurrentSession(@AuthSession AuthUser user) {
        return ResponseEntity.ok(new ResponseDto<>(user.getCurrentUser()));
    }
}