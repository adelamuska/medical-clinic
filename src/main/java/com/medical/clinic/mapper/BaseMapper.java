package com.medical.clinic.mapper;

import com.medical.clinic.dto.DoctorDTO;
import com.medical.clinic.dto.PatientDTO;
import com.medical.clinic.dto.UserDTO;
import com.medical.clinic.entity.DoctorEntity;
import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.entity.UserEntity;

public interface BaseMapper <E,D>{
    D toDto(E e);
    E toEntity(D d);
}
