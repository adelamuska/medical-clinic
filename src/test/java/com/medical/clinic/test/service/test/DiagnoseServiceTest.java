package com.medical.clinic.test.service.test;

import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.dto.DiagnoseDTO;
import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.entity.DiagnoseEntity;
import com.medical.clinic.filter.AppointmentFilter;
import com.medical.clinic.filter.DiagnoseFilter;
import com.medical.clinic.repository.DiagnoseRepository;
import com.medical.clinic.service.DiagnoseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
@ExtendWith(MockitoExtension.class)
public class DiagnoseServiceTest {

    @InjectMocks
    DiagnoseService diagnoseService;

    @Mock
    DiagnoseRepository diagnoseRepository;

    @Test
    void test_get_all_diagnoses() {
        var filter = DiagnoseFilter.builder()
                .dateDiagnosed(LocalDate.of(2024, 6, 1))
                .diagnosis("Stomach ache")
                .details("Poisoning")
                .build();

        // Initialize pageable with some sample values
        var pageable = PageRequest.of(0, 10);

        List<DiagnoseEntity> diagnoseEntities = new ArrayList<>();
        // Add some sample appointment entities
        diagnoseEntities.add(new DiagnoseEntity());
        diagnoseEntities.add(new DiagnoseEntity());
        // Create a PageImpl object from the list
        Page<DiagnoseEntity> diagnosePage = new PageImpl<>(diagnoseEntities);

        // Mock the repository method
        when(diagnoseRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(diagnosePage);

        // Call the service method
        Page<DiagnoseEntity> result = diagnoseService.getAllDiagnoses(filter, pageable);

        // Assert the result
        assertEquals(diagnosePage, result);
    }

    @Test
    void test_add_diagnose() {

        var diagnoseDTO = new DiagnoseDTO();
        diagnoseDTO.setDiagnosisId(1234);

        var diagnoseEntity = new DiagnoseEntity();
        diagnoseEntity.setDiagnosisId(1234);
        // Mock the mapper to convert DTO to Entity and back
        when(diagnoseRepository.save(any(DiagnoseEntity.class))).thenReturn(diagnoseEntity);

        // Call the method to test
        DiagnoseDTO result = diagnoseService.addDiagnose(diagnoseDTO);

        // Assert the result
        Assertions.assertEquals(diagnoseDTO, result);

    }


    @Test
    public void test_find_by_Id() {
        // Mock the behavior of appointmentRepository.findById
        DiagnoseEntity diagnoseEntity = new DiagnoseEntity(); // Create a dummy AppointmentEntity
        diagnoseEntity.setDiagnosisId(1234); // Set the appointmentId for the dummy entity

        when(diagnoseRepository.findById(anyInt()))
                .thenReturn(Optional.of(diagnoseEntity)); // Return the dummy entity when findById is called

        // Call the method to be tested
        DiagnoseDTO result = diagnoseService.findById(1234);

        // Verify that the correct method was called with the correct argument
        verify(diagnoseRepository).findById(1234);

        // Check if the result is not null
        Assertions.assertNotNull(result);
        // Check if the appointmentId in the result matches the appointmentId of the dummy entity
        Assertions.assertEquals(1234, result.getDiagnosisId());
    }

    @Test
    void test_update_diagnose() {

        var diagnoseDTO = new DiagnoseDTO();
        diagnoseDTO.setDiagnosisId(1234);

        var diagnoseEntity = new DiagnoseEntity();
        diagnoseEntity.setDiagnosisId(1234);
        // Mock the mapper to convert DTO to Entity and back
        when(diagnoseRepository.findById(diagnoseDTO.getDiagnosisId())).thenReturn(Optional.of(diagnoseEntity));
        when(diagnoseRepository.save(any(DiagnoseEntity.class))).thenReturn(diagnoseEntity);

        // Call the method to test
        DiagnoseDTO result = diagnoseService.updateDiagnose(diagnoseDTO);

        // Assert the result
        assertEquals(diagnoseDTO, result);
    }


    @Test
    public void test_delete_by_Id() {
        // Arrange
        Integer existingDiagnoseId = 1;
        DiagnoseEntity existingDiagnose = new DiagnoseEntity();
        existingDiagnose.setDiagnosisId(existingDiagnoseId);

        // Mock behavior
        when(diagnoseRepository.findById(existingDiagnoseId)).thenReturn(java.util.Optional.of(existingDiagnose));

        // Act
        diagnoseService.deleteById(existingDiagnoseId);

        // Assert
        verify(diagnoseRepository).findById(existingDiagnoseId);
        verify(diagnoseRepository).setDeleteTrue(existingDiagnoseId);
    }


}
