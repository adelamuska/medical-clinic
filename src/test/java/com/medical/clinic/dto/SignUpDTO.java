package com.medical.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {

    private String username;
    private String email;
    private String contactNumber;
    private String password;
    private String role;

}
