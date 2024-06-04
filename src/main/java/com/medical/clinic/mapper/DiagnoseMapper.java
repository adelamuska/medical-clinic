package com.medical.clinic.mapper;

import com.medical.clinic.dto.DiagnoseDTO;
import com.medical.clinic.entity.DiagnoseEntity;
import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public abstract class DiagnoseMapper implements BaseMapper<DiagnoseEntity, DiagnoseDTO> {

    public static DiagnoseMapper DIAGNOSE_MAPPER= Mappers.getMapper(DiagnoseMapper.class);

    public abstract DiagnoseDTO toDto(DiagnoseEntity diagnoseEntity);

    public abstract DiagnoseEntity toEntity(DiagnoseDTO diagnoseDTO);

}
