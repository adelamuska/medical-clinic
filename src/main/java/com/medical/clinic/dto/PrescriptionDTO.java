package com.medical.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionDTO {

    private Integer prescriptionId;
    private LocalDate dateIssued;
    private String medication;
    private String dosage;
    private String instruction;
    //private PatientDTO patientsDTO;
    //private DoctorDTO doctorsDTO;
}
