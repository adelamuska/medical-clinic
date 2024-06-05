package com.medical.clinic.test.service.test;


import com.medical.clinic.dto.DoctorDTO;
import com.medical.clinic.entity.DoctorEntity;
import com.medical.clinic.filter.DoctorFilter;
import com.medical.clinic.mapper.DoctorMapper;
import com.medical.clinic.repository.DoctorRepository;
import com.medical.clinic.service.DoctorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DoctorServiceTest {
    @InjectMocks
    DoctorService doctorService;

    @Mock
    DoctorRepository doctorRepository;

    @Mock
    DoctorMapper doctorMapper;

    @Test
    void test_get_all_doctors() {
        var filter = DoctorFilter.builder()
                .firstName("Jim")
                .lastName("Carry")
                .specialization("Dentist")
                .build();


        var pageable = PageRequest.of(0, 10);

        List<DoctorEntity> doctorEntities = new ArrayList<>();
        doctorEntities.add(new DoctorEntity());
        doctorEntities.add(new DoctorEntity());

        Page<DoctorEntity> doctorPage = new PageImpl<>(doctorEntities);

        when(doctorRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(doctorPage);

        Page<DoctorEntity> result = doctorService.getAllDoctors(filter, pageable);

        assertEquals(doctorPage, result);
    }



    @Test
    void test_add_doctor() {

        var doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(1234);

        var doctorEntity = new DoctorEntity();
        doctorEntity.setDoctorId(1234);
        doctorDTO.setDiagnosesDTO(new ArrayList<>());
        doctorDTO.setPrescriptionsDTO(new ArrayList<>());
        doctorDTO.setAppointmentsDTO(new ArrayList<>());

        when(doctorRepository.save(any(DoctorEntity.class))).thenReturn(doctorEntity);

        DoctorDTO result = doctorService.addDoctor(doctorDTO);

        Assertions.assertEquals(doctorDTO, result);

    }


    @Test
    public void test_find_by_Id() {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setDoctorId(1234);

        when(doctorRepository.findById(anyInt()))
                .thenReturn(Optional.of(doctorEntity));

        DoctorDTO result = doctorService.findById(doctorEntity.getDoctorId());

        Assertions.assertNotNull(result);
    }

    @Test
    void test_update_doctor() {

        var doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(1234);
        doctorDTO.setSpecialization("Surgeon");

        var doctorEntity = new DoctorEntity();
        doctorEntity.setDoctorId(1234);
        doctorEntity.setSpecialization("Surgeon");
        doctorDTO.setDiagnosesDTO(new ArrayList<>());
        doctorDTO.setPrescriptionsDTO(new ArrayList<>());
        doctorDTO.setAppointmentsDTO(new ArrayList<>());

        when(doctorRepository.findById(doctorDTO.getDoctorId())).thenReturn(Optional.of(doctorEntity));
        when(doctorMapper.toEntity(doctorDTO)).thenReturn(doctorEntity);
        when(doctorRepository.save(any(DoctorEntity.class))).thenReturn(doctorEntity);
        when(doctorMapper.toDto(doctorEntity)).thenReturn(doctorDTO);
        DoctorDTO result = doctorService.updateDoctor(doctorDTO);

        assertEquals(doctorDTO, result);
    }



    @Test
    public void test_delete_by_Id() {
        Integer doctorId = 1;
        DoctorEntity existingDoctor = new DoctorEntity();
        existingDoctor.setDoctorId(doctorId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(existingDoctor));

        doctorService.deleteById(doctorId);
    }
}
