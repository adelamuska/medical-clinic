package com.medical.clinic.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionFilter {

    private LocalDate dateIssued;
    private String medication;
    private String dosage;
    private String instruction;
    private String sortBy;
    private String order;
}
