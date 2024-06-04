package com.medical.clinic.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentFilter {

    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private String status;
    private String sortBy;
    private String order;
}
