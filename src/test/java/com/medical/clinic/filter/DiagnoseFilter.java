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
public class DiagnoseFilter {

    private LocalDate dateDiagnosed;
    private String diagnosis;
    private String details;
    private String sortBy;
    private String order;
}
