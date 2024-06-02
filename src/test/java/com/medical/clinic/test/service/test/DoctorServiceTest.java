package com.medical.clinic.test.service.test;

import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.dto.DiagnoseDTO;
import com.medical.clinic.dto.DoctorDTO;
import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.entity.DiagnoseEntity;
import com.medical.clinic.entity.DoctorEntity;
import com.medical.clinic.filter.AppointmentFilter;
import com.medical.clinic.filter.DoctorFilter;
import com.medical.clinic.mapper.DoctorMapper;
import com.medical.clinic.repository.DiagnoseRepository;
import com.medical.clinic.repository.DoctorRepository;
import com.medical.clinic.service.DiagnoseService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
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

        // Initialize pageable with some sample values
        var pageable = PageRequest.of(0, 10);

        List<DoctorEntity> doctorEntities = new ArrayList<>();
        // Add some sample appointment entities
        doctorEntities.add(new DoctorEntity());
        doctorEntities.add(new DoctorEntity());
        // Create a PageImpl object from the list
        Page<DoctorEntity> dentistPage = new PageImpl<>(doctorEntities);

        // Mock the repository method
        when(doctorRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(dentistPage);

        // Call the service method
        Page<DoctorEntity> result = doctorService.getAllDoctors(filter, pageable);

        // Assert the result
        assertEquals(dentistPage, result);
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
        // Mock the mapper to convert DTO to Entity and back
        when(doctorRepository.save(any(DoctorEntity.class))).thenReturn(doctorEntity);

        // Call the method to test
        DoctorDTO result = doctorService.addDoctor(doctorDTO);

        // Assert the result
        Assertions.assertEquals(doctorDTO, result);

    }


    @Test
    public void test_find_by_Id() {
        // Mock the behavior of appointmentRepository.findById
        DoctorEntity doctorEntity = new DoctorEntity(); // Create a dummy AppointmentEntity
        doctorEntity.setDoctorId(1234); // Set the appointmentId for the dummy entity

        when(doctorRepository.findById(anyInt()))
                .thenReturn(Optional.of(doctorEntity)); // Return the dummy entity when findById is called

        // Call the method to be tested
        DoctorDTO result = doctorService.findById(1234);

        // Verify that the correct method was called with the correct argument
        verify(doctorRepository).findById(1234);

        // Check if the result is not null
        Assertions.assertNotNull(result);
        // Check if the appointmentId in the result matches the appointmentId of the dummy entity
        Assertions.assertEquals(1234, result.getDoctorId());
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

        // Mock repository and mapper interactions
        when(doctorRepository.findById(doctorDTO.getDoctorId())).thenReturn(Optional.of(doctorEntity));
        when(doctorMapper.toEntity(doctorDTO)).thenReturn(doctorEntity);
        when(doctorRepository.save(any(DoctorEntity.class))).thenReturn(doctorEntity);
        when(doctorMapper.toDto(doctorEntity)).thenReturn(doctorDTO);
        // Call the method to test
        DoctorDTO result = doctorService.updateDoctor(doctorDTO);

        // Assert the result
        assertEquals(doctorDTO, result);
    }



    @Test
    public void test_delete_by_Id() {
        // Arrange
        Integer doctorId = 1;
        DoctorEntity existingDoctor = new DoctorEntity();
        existingDoctor.setDoctorId(doctorId);

        // Mock behavior
        when(doctorRepository.findById(doctorId)).thenReturn(java.util.Optional.of(existingDoctor));

        // Act
        doctorService.deleteById(doctorId);

        // Assert
        verify(doctorRepository).findById(doctorId);
        verify(doctorRepository).setDeleteTrue(doctorId);
    }
}
