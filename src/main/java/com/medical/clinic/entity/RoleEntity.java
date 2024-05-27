package com.medical.clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_Id")
    private Integer roleId;
    @Column(name="role_name")
    @NotNull(message = "{validation.entity.role.roleName}")
    @Pattern(regexp = "patient|doctor", message = "{validation.entity.role.invalid}")
    private String roleName;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name= "role_Id"),
            inverseJoinColumns = @JoinColumn(name= "user_ID"))
    private List<UserEntity> roleUsers = new ArrayList<>();

    @Override
    public String toString() {
        return "RoleEntity{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
