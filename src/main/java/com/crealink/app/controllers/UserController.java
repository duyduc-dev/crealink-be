package com.crealink.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crealink.app.constants.AppPath;
import com.crealink.app.dto.response.ResponseDto;
import com.crealink.app.dto.user.UsernameAvailabilityDto;
import com.crealink.app.services.UserService;
import com.crealink.app.validators.ValidUsername;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.USER_API)
@Validated
public class UserController {

        private final UserService userService;

        @GetMapping(AppPath.USERNAME_AVAILABILITY)
        public ResponseEntity<ResponseDto<UsernameAvailabilityDto>> checkUsernameAvailability(
                        @Size(min = 3, max = 30, message = "This field must have size between 3 and 30 characters") @ValidUsername @RequestParam String username) {

                boolean available = userService.checkUsernameAvailability(username);

                UsernameAvailabilityDto data = UsernameAvailabilityDto.builder()
                                .available(available)
                                .build();

                ResponseDto<UsernameAvailabilityDto> response = new ResponseDto<>(data);

                return ResponseEntity.ok(response);
        }
}