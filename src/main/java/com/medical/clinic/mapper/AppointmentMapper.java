package com.medical.clinic.mapper;

import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public abstract class AppointmentMapper implements BaseMapper<AppointmentEntity, AppointmentDTO>{

    public static AppointmentMapper APPOINTMENT_MAPPER= Mappers.getMapper(AppointmentMapper.class);



    public abstract AppointmentDTO toDto(AppointmentEntity appointmentEntity);


    public abstract AppointmentEntity toEntity(AppointmentDTO appointmentDTO);

}
