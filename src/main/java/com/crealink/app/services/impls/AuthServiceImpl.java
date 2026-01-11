package com.crealink.app.services.impls;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crealink.app.configs.security.JwtService;
import com.crealink.app.dto.auth.SignInRequestDto;
import com.crealink.app.dto.auth.SignInResponseDto;
import com.crealink.app.dto.auth.SignupRequestDto;
import com.crealink.app.dto.auth.SignupResponseDto;
import com.crealink.app.dto.response.ResponseStatus;
import com.crealink.app.dto.user.UserDto;
import com.crealink.app.exceptions.UnauthorizedException;
import com.crealink.app.mapper.UserMapper;
import com.crealink.app.services.AuthService;
import com.crealink.app.services.UserService;
import com.crealink.app.utilities.PasswordUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignupResponseDto signup(SignupRequestDto request) {
        UserDto newUser = UserDto.builder()
                .email(request.email())
                .username(request.username())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .dateOfBirth(request.dateOfBirth())
                .password(request.password())
                .build();
        UserDto newUserSaved = userService.createUser(newUser, passwordEncoder);
        String token = jwtService.generateToken(newUserSaved);
        return new SignupResponseDto(token);
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto request) {
        UserDto user = userService.findByEmailOrUsername(request.username()).map(userMapper::toDto)
                .orElseThrow(() -> new UnauthorizedException(ResponseStatus.USERNAME_PASSWORD_INVALID));
        boolean isPasswordValid = PasswordUtil.checkPassword(passwordEncoder, request.password(), user.passwordSalt(),
                user.password());
        if (!isPasswordValid) {
            throw new UnauthorizedException(ResponseStatus.USERNAME_PASSWORD_INVALID);
        }
        String token = jwtService.generateToken(user);
        return new SignInResponseDto(token);
    }

}
