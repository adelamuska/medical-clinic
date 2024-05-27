package com.medical.clinic.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_roles")
public class UserRoleEntity {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @JoinColumn(name = "user_Id", referencedColumnName = "user_Id")
    @MapsId("userId")
    private UserEntity users;

    @ManyToOne
    @JoinColumn(name = "role_Id", referencedColumnName = "role_Id")
    @MapsId("roleId")
    private RoleEntity roles;
}
