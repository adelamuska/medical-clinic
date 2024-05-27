package com.medical.clinic.controller;

import com.medical.clinic.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public String token(@RequestBody Authentication authentication){
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return tokenService.generateToken(authentication);
    }

}
