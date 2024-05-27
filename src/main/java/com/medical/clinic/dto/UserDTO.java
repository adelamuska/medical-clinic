package com.medical.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {


    private Integer userId;
    private String username;
    private String password;
//    private String role;
    private String email;
    private String contactNumber;
    private List<PatientDTO> patientsDTO;
    private List<DoctorDTO> doctorsDTO;
    private List<RoleDTO> userRolesDTO;

}
