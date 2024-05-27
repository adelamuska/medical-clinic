package com.medical.clinic.mapper;

import com.medical.clinic.dto.*;
import com.medical.clinic.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


import static com.medical.clinic.mapper.DiagnoseMapper.DIAGNOSE_MAPPER;
import static com.medical.clinic.mapper.DiagnoseMapper.DOCTOR_MAPPER;

@Mapper(componentModel = "spring")
public abstract class PatientMapper implements BaseMapper<PatientEntity, PatientDTO> {

   public static PatientMapper PATIENT_MAPPER= Mappers.getMapper(PatientMapper.class);
   public static UserMapper USER_MAPPER= Mappers.getMapper(UserMapper.class);
   public static AppointmentMapper APPOINTMENT_MAPPER= Mappers.getMapper(AppointmentMapper.class);
   public static PrescriptionMapper PRESCRIPTION_MAPPER= Mappers.getMapper(PrescriptionMapper.class);


   @Mapping(source = "prescriptions", target = "prescriptionsDTO",qualifiedByName = "mapPrescriptionDto")
   @Mapping(source = "appointments", target = "appointmentsDTO",qualifiedByName = "mapAppointmentDto")
   @Mapping(source = "diagnosePatient", target = "diagnosePatientDTO", qualifiedByName = "mapPatientDiagnoseDto")
   @Mapping(source = "doctorPatient", target = "doctorPatientDTO",qualifiedByName = "mapDoctorPatientDto")
   //@Mapping(source = "users", target = "usersDTO",qualifiedByName = "mapUserDto")
   public abstract PatientDTO toDto(PatientEntity entity);

   @Mapping(source = "prescriptionsDTO", target = "prescriptions")
   @Mapping(source = "appointmentsDTO", target = "appointments")
   @Mapping(source = "doctorPatientDTO", target = "doctorPatient")
   //@Mapping(source = "usersDTO", target = "users")
   @Mapping(source = "diagnosePatientDTO", target = "diagnosePatient")
   public abstract PatientEntity toEntity(PatientDTO dto);


   @Named("mapUserDto")
   UserDTO mapUser(UserEntity entity){
      if(entity != null)
         return USER_MAPPER.toDto(entity);
      return null;
   }
   @Named("mapPrescriptionDto")
   PrescriptionDTO mapPrescription(PrescriptionEntity entity){
      if(entity != null)
         return PRESCRIPTION_MAPPER.toDto(entity);
      return null;
   }

   @Named("mapAppointmentDto")
  public AppointmentDTO mapAppointment(AppointmentEntity entity){
      if(entity != null)
         return APPOINTMENT_MAPPER.toDto(entity);
      return null;
   }
   @Named("mapPatientDiagnoseDto")
   public DiagnoseDTO mapPatientDiagnose(DiagnoseEntity entity){
      if(entity != null)
         return DIAGNOSE_MAPPER.toDto(entity);
      return null;
   }

   @Named("mapDoctorPatientDto")
   public DoctorDTO mapDoctorPatient(DoctorEntity entity){
      if(entity != null)
         return DOCTOR_MAPPER.toDto(entity);
      return null;
   }


}
