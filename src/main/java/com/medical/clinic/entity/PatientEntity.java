package com.medical.clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="patient_Id")
    private Integer patientId;
    @Column(name="first_name")
    @NotNull(message = "{validation.entity.patients.firstNameNull}")
    @Size(min = 2, message = "{validation.entity.patient.firstName}")
    private String firstName;
    @Column(name="last_name")
    @NotNull(message = "{validation.entity.patients.lastNameNull}")
    @Size(min = 2, message = "{validation.entity.patient.lastName}")
    private String lastName;
    @Column(name="birth_date")
    @NotNull(message = "{validation.entity.patients.birthDate}")
    private LocalDate birthDate;
    @NotNull(message = "{validation.entity.patients.genderNull}")
    @Pattern(regexp = "Male|Female|Non-Binary|Other", message = "{validation.entity.patients.gender}")
    private String gender;
    private String address;


    @OneToMany(mappedBy = "patients",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionEntity> prescriptions = new ArrayList<>();

    @OneToMany(mappedBy = "patients",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentEntity> appointments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name= "user_Id", referencedColumnName = "user_Id")
    private UserEntity users;

    @ManyToMany(mappedBy = "patientDoctor")
    private List<DoctorEntity> doctorPatient = new ArrayList<>();

    @ManyToMany(mappedBy = "patientDiagnose")
    private List<DiagnoseEntity> diagnosePatient = new ArrayList<>();

    @Override
    public String toString() {
        return "PatientEntity{" +
                "patientId=" + patientId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", users=" + users +
                '}';
    }
}
