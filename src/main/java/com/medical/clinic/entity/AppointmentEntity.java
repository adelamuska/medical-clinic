package com.medical.clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_Id")
    private Integer appointmentId;
    @Column(name="appointment_Date")
    @NotNull(message = "{validation.entity.appointment.appointmentDateNull}")
    @Future(message = "{validation.entity.appointment.appointmentDate}")
    private LocalDate appointmentDate;
    @Column(name="start_time")
    @NotNull(message = "{validation.entity.appointment.startTime}")
    private LocalTime startTime;
    @Column(name="end_time")
    @NotNull(message = "{validation.entity.appointment.endTime}")
    private LocalTime endTime;
    @Size(max = 500, message = "{validation.entity.appointment.description}")
    @NotNull(message = "{validation.entity.appointment.description}")
    private String description;
    @Pattern(regexp = "Scheduled|Cancelled|Completed", message = "{validation.entity.appointment.status}")
    @NotNull(message = "{validation.entity.appointment.status}")
    private String status;

    @ManyToOne()
    @JoinColumn(name = "patient_Id",referencedColumnName = "patient_Id")
    private PatientEntity patients;

    @ManyToOne()
    @JoinColumn(name= "doctor_Id", referencedColumnName = "doctor_Id")
    private DoctorEntity doctors;

    @Override
    public String toString() {
        return "AppointmentEntity{" +
                "appointmentId=" + appointmentId +
                ", appointmentDate=" + appointmentDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", patients=" + patients +
                ", doctors=" + doctors +
                '}';
    }
}
