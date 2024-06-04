package com.medical.clinic.mapper;

import com.medical.clinic.dto.*;
import com.medical.clinic.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import static com.medical.clinic.mapper.PatientMapper.PATIENT_MAPPER;
import static com.medical.clinic.mapper.DoctorMapper.DOCTOR_MAPPER;
@Mapper(componentModel = "spring")
public abstract class UserMapper implements BaseMapper<UserEntity, UserDTO> {

    public static UserMapper USER_MAPPER= Mappers.getMapper(UserMapper.class);


    @Mapping(source = "patients", target = "patientsDTO",qualifiedByName = "mapPatientDto")
    @Mapping(source = "doctors", target = "doctorsDTO",qualifiedByName = "mapDoctorDto")
    public abstract UserDTO toDto(UserEntity userEntity);

    @Mapping(source = "patientsDTO", target = "patients")
    @Mapping(source = "doctorsDTO", target = "doctors")
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

}
