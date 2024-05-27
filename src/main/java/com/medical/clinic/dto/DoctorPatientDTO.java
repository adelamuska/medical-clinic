package com.medical.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorPatientDTO {

    private DoctorPatientIdDTO id;
    private DoctorDTO doctorsDTO;
    private PatientDTO patientsDTO;
}
