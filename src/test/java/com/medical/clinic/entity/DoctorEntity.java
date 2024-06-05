package com.medical.clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="doctors")
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="doctor_Id")
    private Integer doctorId;
    @Column(name="first_name")
    @NotNull(message = "{validation.entity.doctors.firstNameNull}")
    @Size(min = 2, message = "{validation.entity.doctor.firstName}")
    private String firstName;
    @NotNull(message = "{validation.entity.doctors.lastNameNull}")
    @Column(name="last_name")
    @Size(min = 2, message = "{validation.entity.doctors.lastName}")
    private String lastName;
    @NotNull(message = "{validation.entity.doctors.specialization}")
    private String specialization;

    @OneToMany(mappedBy = "doctors",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiagnoseEntity> diagnoses = new ArrayList<>();

    @OneToMany(mappedBy = "doctors",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionEntity> prescriptions = new ArrayList<>();

    @OneToMany(mappedBy = "doctors",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentEntity> appointments = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "user_Id", referencedColumnName = "user_Id")
    private UserEntity users;

    @ManyToMany
    @JoinTable(name = "doctor_patient",
    joinColumns = @JoinColumn(name= "doctor_Id"),
    inverseJoinColumns = @JoinColumn(name= "patient_Id"))
    private List<PatientEntity> patientDoctor = new ArrayList<>();


    @Override
    public String toString() {
        return "DoctorEntity{" +
                "doctorId=" + doctorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization='" + specialization + '\'' +
                ", users=" + users +
                '}';
    }
}
