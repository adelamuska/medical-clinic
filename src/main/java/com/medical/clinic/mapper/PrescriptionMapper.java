package com.medical.clinic.mapper;

import com.medical.clinic.dto.DoctorDTO;
import com.medical.clinic.dto.PatientDTO;
import com.medical.clinic.dto.PrescriptionDTO;
import com.medical.clinic.entity.DoctorEntity;
import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.entity.PrescriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public abstract class PrescriptionMapper implements BaseMapper<PrescriptionEntity, PrescriptionDTO> {

    public static PrescriptionMapper PRESCRIPTION_MAPPER= Mappers.getMapper(PrescriptionMapper.class);
    public static PatientMapper PATIENT_MAPPER= Mappers.getMapper(PatientMapper.class);
    public static DoctorMapper DOCTOR_MAPPER = Mappers.getMapper(DoctorMapper.class);


   // @Mapping(source = "patients", target = "patientsDTO",qualifiedByName = "mapPatientDto")
   // @Mapping(source = "doctors", target = "doctorsDTO",qualifiedByName = "mapDoctorDto")
    public abstract PrescriptionDTO toDto(PrescriptionEntity entity);

   // @Mapping(source = "patientsDTO", target = "patients")
    //@Mapping(source = "doctorsDTO", target = "doctors")
    public abstract PrescriptionEntity toEntity(PrescriptionDTO dto);

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
