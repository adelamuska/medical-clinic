package com.medical.clinic.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilter {
    private String username;
    private String password;
    private String role;
    private String email;
    private String contactNumber;
    private String sortBy;
    private String order;
}
