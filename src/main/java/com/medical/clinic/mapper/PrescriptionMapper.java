package com.medical.clinic.mapper;

import com.medical.clinic.dto.PrescriptionDTO;
import com.medical.clinic.entity.PrescriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public abstract class PrescriptionMapper implements BaseMapper<PrescriptionEntity, PrescriptionDTO> {

    public static PrescriptionMapper PRESCRIPTION_MAPPER= Mappers.getMapper(PrescriptionMapper.class);
    public abstract PrescriptionDTO toDto(PrescriptionEntity entity);

    public abstract PrescriptionEntity toEntity(PrescriptionDTO dto);

}
