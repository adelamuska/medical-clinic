package com.medical.clinic.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="doctor_patient")
public class DoctorPatientEntity {

    @EmbeddedId
    private DoctorPatientId id;

    @ManyToOne
    @JoinColumn(name = "doctor_Id", referencedColumnName = "doctor_Id")
    @MapsId("doctorId")
    private DoctorEntity doctors;

    @ManyToOne
    @JoinColumn(name = "patient_Id", referencedColumnName = "patient_Id")
    @MapsId("patientId")
    private PatientEntity patients;

    @Override
    public String toString() {
        return "DoctorPatientEntity{" +
                "id=" + id +
                ", doctors=" + doctors +
                ", patients=" + patients +
                '}';
    }
}
