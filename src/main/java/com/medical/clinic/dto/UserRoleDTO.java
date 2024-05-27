package com.medical.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleDTO {

    private UserRoleIdDTO id;
    private UserDTO usersDTO;
    private RoleDTO rolesDTO;
}
