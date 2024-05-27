package com.medical.clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="prescriptions")
public class PrescriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="prescription_Id")
    private Integer prescriptionId;
    @Column(name="date_issued")
    @NotNull(message = "{validation.entity.prescriptions.dateIssuedNull}")
    @PastOrPresent(message = "{validation.entity.diagnose.dateIssued}")
    private LocalDate dateIssued;
    @NotNull(message = "{validation.entity.prescriptions.medicationNull}")
    @Size(max = 255, message = "{validation.entity.diagnose.medication}")
    private String medication;
    @NotNull(message = "{validation.entity.prescriptions.dosageNull}")
    @Size(max = 100, message = "{validation.entity.diagnose.dosage}")
    private String dosage;
    @NotNull(message = "{validation.entity.prescriptions.instructionNull}")
    @Size(max = 500, message = "{validation.entity.diagnose.instructions}")
    private String instruction;

    @ManyToOne
    @JoinColumn(name = "patient_Id",referencedColumnName = "patient_Id")
    private PatientEntity patients;

    @ManyToOne()
    @JoinColumn(name= "doctor_Id", referencedColumnName = "doctor_Id")
    private DoctorEntity doctors;

    @Override
    public String toString() {
        return "PrescriptionEntity{" +
                "prescriptionId=" + prescriptionId +
                ", dateIssued=" + dateIssued +
                ", medication='" + medication + '\'' +
                ", dosage='" + dosage + '\'' +
                ", instruction='" + instruction + '\'' +
                ", patients=" + patients +
                ", doctors=" + doctors +
                '}';
    }
}
