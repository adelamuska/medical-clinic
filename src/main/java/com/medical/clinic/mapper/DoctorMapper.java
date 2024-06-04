package com.medical.clinic.mapper;

import com.medical.clinic.dto.*;
import com.medical.clinic.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import static com.medical.clinic.mapper.DiagnoseMapper.DIAGNOSE_MAPPER;
import static com.medical.clinic.mapper.AppointmentMapper.APPOINTMENT_MAPPER;
import static com.medical.clinic.mapper.PrescriptionMapper.PRESCRIPTION_MAPPER;

@Mapper(componentModel = "spring")
public abstract class DoctorMapper implements BaseMapper<DoctorEntity, DoctorDTO> {

    public static DoctorMapper DOCTOR_MAPPER = Mappers.getMapper(DoctorMapper.class);

    @Mapping(source = "diagnoses", target = "diagnosesDTO",qualifiedByName = "mapDiagnoseDto")
    @Mapping(source = "prescriptions", target = "prescriptionsDTO",qualifiedByName = "mapPrescriptionDto")
    @Mapping(source = "appointments", target = "appointmentsDTO",qualifiedByName = "mapAppointmentDto")
    public abstract DoctorDTO toDto(DoctorEntity doctorEntity);

    @Mapping(source = "diagnosesDTO", target = "diagnoses")
    @Mapping(source = "prescriptionsDTO", target = "prescriptions")
    @Mapping(source = "appointmentsDTO", target = "appointments")
    public abstract DoctorEntity toEntity(DoctorDTO doctorDTO);


    @Named("mapDiagnoseDto")
    DiagnoseDTO mapDiagnose(DiagnoseEntity entity){
        if(entity != null)
            return DIAGNOSE_MAPPER.toDto(entity);
        return null;
    }

    @Named("mapPrescriptionDto")
    PrescriptionDTO mapPrescription(PrescriptionEntity entity){
        if(entity != null)
            return PRESCRIPTION_MAPPER.toDto(entity);
        return null;
    }

    @Named("mapAppointmentDto")
    AppointmentDTO mapAppointment(AppointmentEntity entity){
        if(entity != null)
            return APPOINTMENT_MAPPER.toDto(entity);
        return null;
    }


}
