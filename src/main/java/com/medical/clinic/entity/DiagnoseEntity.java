package com.medical.clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="diagnoses")
public class DiagnoseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diagnosis_Id")
    private Integer diagnosisId;
    @Column(name="date_diagnosed")
    @NotNull(message = "{validation.entity.diagnoses.dateDiagnosedNull}")
    @PastOrPresent(message = "{validation.entity.diagnose.dateDiagnosed}")
    private LocalDate dateDiagnosed;
    @NotNull(message = "{validation.entity.diagnoses.diagnosisNull}")
    @Size(max = 255, message = "{validation.entity.diagnose.diagnosis}")
    private String diagnosis;
    @Size(max = 1000, message = "{validation.entity.diagnose.details}s")
    private String details;


    @ManyToOne()
    @JoinColumn(name= "doctor_Id", referencedColumnName = "doctor_Id")
    private DoctorEntity doctors;

    @ManyToMany
    @JoinTable(name = "patient_diagnoses",
            joinColumns = @JoinColumn(name= "patient_Id"),
            inverseJoinColumns = @JoinColumn(name= "diagnosis_Id"))
    private List<PatientEntity> patientDiagnose = new ArrayList<>();


    @Override
    public String toString() {
        return "DiagnoseEntity{" +
                "diagnosisId=" + diagnosisId +
                ", dateDiagnosed=" + dateDiagnosed +
                ", diagnosis='" + diagnosis + '\'' +
                ", details='" + details + '\'' +
                ", doctors=" + doctors +
                '}';
    }
}
