package com.medical.clinic.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class PatientDiagnoseId implements Serializable {

    private Integer patient_Id;
    private Integer diagnosis_Id;
}
