package com.medical.clinic.service;

import com.medical.clinic.dto.UserDTO;
import com.medical.clinic.dto.SignUpDTO;
import com.medical.clinic.dto.UserDTOSecurity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.medical.clinic.entity.UserEntity;

import java.nio.CharBuffer;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceSecurity implements UserDetailsService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    public UserServiceSecurity(PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
    }

    public UserDTOSecurity signUp(SignUpDTO signUpDTO) {
        // TODO vendos logjiken e login per ta
        //  bere signup nga databaza userin
        // TODO dmth do besh save-t dhe validimet e nevojshme
       // TODO mos harro te besh dhe encode te password
        //         si kjo ketu
         //String encodedMasterPassword =passwordEncoder.encode(CharBuffer.wrap(signUpDTO.getPassword()));
        if (userRepository.existsByUsername(signUpDTO.getUsername())) {
            throw new ClassicModelException("Username is already taken.");
        }

        if (userRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new ClassicModelException("Email is already in use.");
        }

        String encodedPassword = passwordEncoder.encode(CharBuffer.wrap(signUpDTO.getPassword()));

        UserEntity user = new UserEntity();
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(signUpDTO.getEmail());
        user.setContactNumber(signUpDTO.getContactNumber());
        user.setRole(signUpDTO.getRole());

        userRepository.save(user);
        // vec se ne vend te 'the-password' do jete passwordi
        // qe vjen prej signUpDTO
        return new UserDTOSecurity(signUpDTO.getUsername(), signUpDTO.getEmail(),signUpDTO.getContactNumber() ,signUpDTO.getPassword(), "token", List.of(signUpDTO.getRole()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticationService.findByUsername(username);
    }
}
