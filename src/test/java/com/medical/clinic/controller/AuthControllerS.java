package com.medical.clinic.controller;

import com.medical.clinic.configuration.UserAuthenticationProvider;
import com.medical.clinic.dto.SignUpDTO;
import com.medical.clinic.dto.UserDTOSecurity;
import com.medical.clinic.service.UserServiceSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerS {

    private final UserServiceSecurity userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/log-in")
    public ResponseEntity<UserDTOSecurity> login(@AuthenticationPrincipal UserDTOSecurity userDTO) throws ParseException {
        userDTO.setToken(userAuthenticationProvider.createToken(userDTO));
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDTOSecurity> register(@RequestBody SignUpDTO signUpDTO) {
        UserDTOSecurity createdUser = userService.signUp(signUpDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/log-out")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDTOSecurity signUpDTO) {
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
}
