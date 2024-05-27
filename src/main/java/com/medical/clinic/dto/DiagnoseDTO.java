package com.medical.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiagnoseDTO {

    private Integer diagnosisId;
    private LocalDate dateDiagnosed;
    private String diagnosis;
    private String details;
    //private DoctorDTO doctorsDTO;
    //private List<PatientDTO> patientDiagnoseDTO;

}
