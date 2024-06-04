package com.medical.clinic.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="patient_diagnoses")
public class PatientDiagnoseEntity {

    @EmbeddedId
    private PatientDiagnoseId id;

    @ManyToOne
    @JoinColumn(name = "patient_Id", referencedColumnName = "patient_Id")
    @MapsId("patientId")
    private PatientEntity patients;

    @ManyToOne
    @JoinColumn(name = "diagnosis_Id", referencedColumnName = "diagnosis_Id")
    @MapsId("diagnosisId")
    private DiagnoseEntity diagnoses;

    @Override
    public String toString() {
        return "PatientDiagnosesEntity{" +
                "id=" + id +
                ", patients=" + patients +
                ", diagnoses=" + diagnoses +
                '}';
    }
}
