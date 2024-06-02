package com.medical.clinic.test.service.test;

import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.dto.DoctorDTO;
import com.medical.clinic.dto.PatientDTO;
import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.entity.DoctorEntity;
import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.filter.AppointmentFilter;
import com.medical.clinic.filter.PatientFilter;
import com.medical.clinic.mapper.PatientMapper;
import com.medical.clinic.repository.DoctorRepository;
import com.medical.clinic.repository.PatientRepository;
import com.medical.clinic.service.DoctorService;
import com.medical.clinic.service.PatientService;
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
public class PatientServiceTest {

    @InjectMocks
    PatientService patientService;

    @Mock
    PatientRepository patientRepository;

    @Mock
    PatientMapper patientMapper;

    @Test
    void test_get_all_patients() {
        var filter = PatientFilter.builder()
                .birthDate(LocalDate.of(2001, 6, 1))
                .firstName("Jerry")
                .lastName("Tom")
                .address("Somewhere")
                .gender("Male")
                .build();

        // Initialize pageable with some sample values
        var pageable = PageRequest.of(0, 10);

        List<PatientEntity> patientEntities = new ArrayList<>();
        // Add some sample appointment entities
        patientEntities.add(new PatientEntity());
        patientEntities.add(new PatientEntity());
        // Create a PageImpl object from the list
        Page<PatientEntity> patientPage = new PageImpl<>(patientEntities);

        // Mock the repository method
        when(patientRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(patientPage);

        // Call the service method
        Page<PatientEntity> result = patientService.getAllPatients(filter, pageable);

        // Assert the result
        assertEquals(patientPage, result);
    }



    @Test
    void test_add_patient() {

        var patientDTO = new PatientDTO();
        patientDTO.setPatientId(1234);
        patientDTO.setPrescriptionsDTO(new ArrayList<>());
        patientDTO.setDiagnosePatientDTO(new ArrayList<>());
        patientDTO.setAppointmentsDTO(new ArrayList<>());
        patientDTO.setDoctorPatientDTO(new ArrayList<>());

        var patientEntity = new PatientEntity();
        patientEntity.setPatientId(1234);

        // Mock the mapper to convert DTO to Entity and back
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patientEntity);

        // Call the method to test
        PatientDTO result = patientService.addPatient(patientDTO);

        // Assert the result
        Assertions.assertEquals(patientDTO, result);

    }

    @Test
    public void test_find_by_Id() {
        // Mock the behavior of appointmentRepository.findById
        PatientEntity patientEntity = new PatientEntity(); // Create a dummy AppointmentEntity
        patientEntity.setPatientId(1234); // Set the appointmentId for the dummy entity

        when(patientRepository.findById(anyInt()))
                .thenReturn(Optional.of(patientEntity)); // Return the dummy entity when findById is called

        // Call the method to be tested
        PatientDTO result = patientService.findById(1234);

        // Verify that the correct method was called with the correct argument
        verify(patientRepository).findById(1234);

        // Check if the result is not null
        Assertions.assertNotNull(result);
        // Check if the appointmentId in the result matches the appointmentId of the dummy entity
        Assertions.assertEquals(1234, result.getPatientId());
    }


    @Test
    void test_update_appointment() {

        var patientDTO = new PatientDTO();
        patientDTO.setPatientId(1234);
        patientDTO.setPrescriptionsDTO(new ArrayList<>());
        patientDTO.setDiagnosePatientDTO(new ArrayList<>());
        patientDTO.setAppointmentsDTO(new ArrayList<>());
        patientDTO.setDoctorPatientDTO(new ArrayList<>());

        var patientEntity = new PatientEntity();
        patientEntity.setPatientId(1234);

        // Mock the mapper to convert DTO to Entity and back
        when(patientRepository.findById(patientDTO.getPatientId())).thenReturn(Optional.of(patientEntity));
        when(patientMapper.toEntity(patientDTO)).thenReturn(patientEntity);
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patientEntity);
        when(patientMapper.toDto(patientEntity)).thenReturn(patientDTO);


        // Call the method to test
        PatientDTO result = patientService.updatePatient(patientDTO);

        // Assert the result
        assertEquals(patientDTO, result);
    }

    @Test
    public void test_delete_by_Id() {
        // Arrange
        Integer patientId = 1;
        PatientEntity existingPatient = new PatientEntity();
        existingPatient.setPatientId(patientId);

        // Mock behavior
        when(patientRepository.findById(patientId)).thenReturn(java.util.Optional.of(existingPatient));

        // Act
        patientService.deleteById(patientId);

        // Assert
        verify(patientRepository).findById(patientId);
        verify(patientRepository).setDeleteTrue(patientId);
    }
}
