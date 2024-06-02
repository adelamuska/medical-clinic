package com.medical.clinic.mapper;

import com.medical.clinic.dto.DoctorDTO;
import com.medical.clinic.dto.PatientDTO;
import com.medical.clinic.dto.RoleDTO;
import com.medical.clinic.dto.UserDTO;
import com.medical.clinic.entity.DoctorEntity;
import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.entity.RoleEntity;
import com.medical.clinic.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import static com.medical.clinic.mapper.RoleMapper.ROLE_MAPPER;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements BaseMapper<UserEntity, UserDTO> {

    public static UserMapper USER_MAPPER= Mappers.getMapper(UserMapper.class);
    public static PatientMapper PATIENT_MAPPER= Mappers.getMapper(PatientMapper.class);
    public static DoctorMapper DOCTOR_MAPPER = Mappers.getMapper(DoctorMapper.class);

    @Mapping(source = "patients", target = "patientsDTO",qualifiedByName = "mapPatientDto")
    @Mapping(source = "doctors", target = "doctorsDTO",qualifiedByName = "mapDoctorDto")
    @Mapping(source = "userRoles", target = "userRolesDTO",qualifiedByName = "mapRoleDto")
    public abstract UserDTO toDto(UserEntity userEntity);

//    @Mapping(source = "patientsDTO", target = "patients")
//    @Mapping(source = "doctorsDTO", target = "doctors")
//    @Mapping(source = "userRolesDTO", target = "userRoles")
    public abstract UserEntity toEntity(UserDTO userDTO);

    @Named("mapPatientDto")
    PatientDTO mapPatient(PatientEntity entity){
        if(entity != null)
            return PATIENT_MAPPER.toDto(entity);
        return null;
    }

    @Named("mapDoctorDto")
    DoctorDTO mapDoctor(DoctorEntity entity){
        if(entity != null)
            return DOCTOR_MAPPER.toDto(entity);
        return null;
    }

    @Named("mapRoleDto")
    RoleDTO mapRole(RoleEntity entity){
        if(entity != null)
            return ROLE_MAPPER.toDto(entity);
        return null;
    }
}
