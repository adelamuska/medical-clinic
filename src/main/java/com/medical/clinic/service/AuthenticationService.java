package com.medical.clinic.service;

import com.medical.clinic.dto.CredentialsDTO;
import com.medical.clinic.dto.UserDTOSecurity;
import com.medical.clinic.entity.UserEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Collections;
import java.util.List;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
    }

    public UserDTOSecurity authenticate(CredentialsDTO credentialsDto) {
        UserEntity user = userRepository.findByUsername(credentialsDto.getUsername())
                .orElseThrow(() -> new ClassicModelException("Invalid username"));

        String encodedPasswordFromDatabase = user.getPassword();

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), encodedPasswordFromDatabase)) {
            return new UserDTOSecurity(user.getUsername(), user.getPassword(), user.getEmail(), user.getContactNumber(), "token", List.of(user.getRole()));
        } else {
            throw new ClassicModelException("Invalid password");
        }
    }

    public UserDTOSecurity findByUsername(String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ClassicModelException("Invalid username"));
        return new UserDTOSecurity(user.getUsername(), user.getPassword(), user.getEmail(), user.getContactNumber(), "token", Collections.EMPTY_LIST);
    }
}
