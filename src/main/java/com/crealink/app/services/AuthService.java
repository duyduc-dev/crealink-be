package com.crealink.app.services;

import com.crealink.app.dto.auth.SignInRequestDto;
import com.crealink.app.dto.auth.SignInResponseDto;
import com.crealink.app.dto.auth.SignupRequestDto;
import com.crealink.app.dto.auth.SignupResponseDto;

public interface AuthService {
     SignupResponseDto signup(SignupRequestDto request);
     SignInResponseDto signIn(SignInRequestDto request);
}
