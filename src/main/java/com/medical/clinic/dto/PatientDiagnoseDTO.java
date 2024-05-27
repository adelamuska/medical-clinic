package com.medical.clinic.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDiagnoseDTO {

    private PatientDiagnoseIdDTO idDTO;
    private PatientDTO patientsDTO;
    private DiagnoseDTO diagnosesDTO;

}
