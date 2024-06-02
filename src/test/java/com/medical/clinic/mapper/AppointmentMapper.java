package com.medical.clinic.mapper;

import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.dto.DoctorDTO;
import com.medical.clinic.dto.PatientDTO;
import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.entity.DoctorEntity;
import com.medical.clinic.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public abstract class AppointmentMapper implements BaseMapper<AppointmentEntity, AppointmentDTO>{

    public static AppointmentMapper APPOINTMENT_MAPPER= Mappers.getMapper(AppointmentMapper.class);
    public static PatientMapper PATIENT_MAPPER= Mappers.getMapper(PatientMapper.class);
    public static DoctorMapper DOCTOR_MAPPER = Mappers.getMapper(DoctorMapper.class);


    //@Mapping(source = "patients", target = "patientsDTO", qualifiedByName = "mapPatientDto")
  //  @Mapping(source = "doctors", target = "doctorsDTO", qualifiedByName = "mapDoctorDto")
    public abstract AppointmentDTO toDto(AppointmentEntity appointmentEntity);


    //@Mapping(source = "patientsDTO", target = "patients")
    //@Mapping(source = "doctorsDTO", target = "doctors")
    public abstract AppointmentEntity toEntity(AppointmentDTO appointmentDTO);
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
