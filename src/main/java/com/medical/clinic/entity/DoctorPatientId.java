package com.medical.clinic.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class DoctorPatientId implements Serializable {

    private Integer patientId;
    private Integer doctorId;
}
