package com.medical.clinic.mapper;

import com.medical.clinic.dto.*;
import com.medical.clinic.entity.*;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.repository.AppointmentRepository;
import com.medical.clinic.repository.DiagnoseRepository;
import com.medical.clinic.repository.PrescriptionRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import static com.medical.clinic.mapper.PrescriptionMapper.PATIENT_MAPPER;

@Mapper(componentModel = "spring")
public abstract class DoctorMapper implements BaseMapper<DoctorEntity, DoctorDTO> {
@Autowired
    DiagnoseRepository diagnoseRepository;

@Autowired
    PrescriptionRepository prescriptionRepository;
@Autowired
    AppointmentRepository appointmentRepository;
    public static DoctorMapper DOCTOR_MAPPER = Mappers.getMapper(DoctorMapper.class);
    public static DiagnoseMapper DIAGNOSE_MAPPER= Mappers.getMapper(DiagnoseMapper.class);
    public static UserMapper USER_MAPPER= Mappers.getMapper(UserMapper.class);
    public static AppointmentMapper APPOINTMENT_MAPPER= Mappers.getMapper(AppointmentMapper.class);
    public static PrescriptionMapper PRESCRIPTION_MAPPER= Mappers.getMapper(PrescriptionMapper.class);


    //@Mapping(source = "users", target = "usersDTO",qualifiedByName = "mapUserDto")
    @Mapping(source = "diagnoses", target = "diagnosesDTO",qualifiedByName = "mapDiagnoseDto")
    @Mapping(source = "prescriptions", target = "prescriptionsDTO",qualifiedByName = "mapPrescriptionDto")
    @Mapping(source = "appointments", target = "appointmentsDTO",qualifiedByName = "mapAppointmentDto")
    //@Mapping(source = "patientDoctor", target = "patientDoctorDTO",qualifiedByName = "doctorPatientDto")
    public abstract DoctorDTO toDto(DoctorEntity doctorEntity);

    //@Mapping(source = "usersDTO", target = "users")
    @Mapping(source = "diagnosesDTO", target = "diagnoses")
    @Mapping(source = "prescriptionsDTO", target = "prescriptions")
    @Mapping(source = "appointmentsDTO", target = "appointments")
   // @Mapping(source = "patientDoctorDTO", target = "patientDoctor")
    public abstract DoctorEntity toEntity(DoctorDTO doctorDTO);

    @Named("mapUserDto")
    UserDTO mapUser(UserEntity entity){
        if(entity != null)
            return USER_MAPPER.toDto(entity);
        return null;
    }

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

    @Named("doctorPatientDto")
   public PatientDTO mapDoctorPatient(PatientEntity entity){
        if(entity != null)
            return PATIENT_MAPPER.toDto(entity);
        return null;
    }



}
