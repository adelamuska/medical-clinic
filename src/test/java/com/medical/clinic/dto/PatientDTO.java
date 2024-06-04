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
public class PatientDTO {

    private Integer patientId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String address;
    private List<PrescriptionDTO> prescriptionsDTO;
    private List<AppointmentDTO> appointmentsDTO;
    private List<DoctorDTO> doctorPatientDTO;
    private List<DiagnoseDTO> diagnosePatientDTO;
}
