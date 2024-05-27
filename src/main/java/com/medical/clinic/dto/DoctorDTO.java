package com.medical.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDTO {

    private Integer doctorId;
    private String firstName;
    private String lastName;
    private String specialization;
    private List<DiagnoseDTO> diagnosesDTO;
    private List<PrescriptionDTO> prescriptionsDTO;
    private List<AppointmentDTO> appointmentsDTO;
    //private UserDTO usersDTO;
    //private List<PatientDTO> patientDoctorDTO;

}
