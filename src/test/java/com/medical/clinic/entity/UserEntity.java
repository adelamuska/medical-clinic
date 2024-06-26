package com.medical.clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Table(name="users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_Id")
    private Integer userId;
    @NotNull(message = "{validation.entity.user.username}")
    @Size(max = 15, message = "{validation.entity.user.usernameSize}")
    private String username;
    @NotNull(message = "{validation.entity.user.password}")
    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "{validation.entity.user.passwordPattern}")
    private String password;
    @NotNull(message = "{validation.entity.user.role}")
    @Pattern(regexp = "patient|doctor", message = "{validation.entity.user.invalid}")
    private String role;
    @NotNull(message = "{validation.entity.user.email}")
    private String email;
    @Column(name="contact_number")
    @Size(min = 10,max = 13, message = "{validation.entity.user.contactNumber}")
    private String contactNumber;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientEntity> patients = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoctorEntity> doctors = new ArrayList<>();


    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }

}
