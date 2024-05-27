package com.medical.clinic.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PatientDiagnoseId implements Serializable {

    private Integer patient_Id;
    private Integer diagnosis_Id;
}
