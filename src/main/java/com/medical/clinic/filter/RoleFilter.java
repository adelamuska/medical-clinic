package com.medical.clinic.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleFilter {

    private String roleName;
    private String sortBy;
    private String order;
}
