package com.medical.clinic.mapper;

import com.medical.clinic.dto.DiagnoseDTO;
import com.medical.clinic.dto.DoctorDTO;
import com.medical.clinic.dto.PatientDTO;
import com.medical.clinic.entity.DiagnoseEntity;
import com.medical.clinic.entity.DoctorEntity;
import com.medical.clinic.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import static com.medical.clinic.mapper.PatientMapper.PATIENT_MAPPER;

@Mapper(componentModel = "spring")
public abstract class DiagnoseMapper implements BaseMapper<DiagnoseEntity, DiagnoseDTO> {

    public static DiagnoseMapper DIAGNOSE_MAPPER= Mappers.getMapper(DiagnoseMapper.class);
    public static DoctorMapper DOCTOR_MAPPER = Mappers.getMapper(DoctorMapper.class);



    //@Mapping(source = "patientDiagnose", target = "patientDiagnoseDTO", qualifiedByName = "mapPatientDiagnoseDto")
  //  @Mapping(source = "doctors", target = "doctorsDTO",qualifiedByName = "mapDoctorDto")
    public abstract DiagnoseDTO toDto(DiagnoseEntity diagnoseEntity);

   // @Mapping(source = "patientDiagnoseDTO", target = "patientDiagnose")
   // @Mapping(source = "doctorsDTO", target = "doctors")
    public abstract DiagnoseEntity toEntity(DiagnoseDTO diagnoseDTO);

    @Named("mapDoctorDto")
    DoctorDTO mapDoctor(DoctorEntity entity){
        if(entity != null)
            return DOCTOR_MAPPER.toDto(entity);
        return null;
    }

    @Named("mapPatientDiagnoseDto")
    PatientDTO mapPatientDiagnose(PatientEntity entity){
        if(entity != null)
            return PATIENT_MAPPER.toDto(entity);
        return null;
    }
}
