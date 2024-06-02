package com.medical.clinic.controller.advice;

import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.exception.GeneralErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<GeneralErrorResponse> handleAppointmentNotFound(ClassicModelException ex, HttpServletRequest request){
        var errorMsg = GeneralErrorResponse.builder()
                .date(LocalDate.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorMsg,HttpStatus.BAD_REQUEST);
    }
}

