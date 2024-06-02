package com.medical.clinic.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GeneralErrorResponse {
    private LocalDate date;
    private int status;
    private String path;
    private String message;
}
