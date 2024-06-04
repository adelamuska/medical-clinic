package com.medical.clinic.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorFilter {
    private String firstName;
    private String lastName;
    private String specialization;
    private String sortBy;
    private String order;
}
