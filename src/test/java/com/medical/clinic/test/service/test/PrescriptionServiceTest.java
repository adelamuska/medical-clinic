package com.medical.clinic.test.service.test;

import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.dto.PatientDTO;
import com.medical.clinic.dto.PrescriptionDTO;
import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.entity.PrescriptionEntity;
import com.medical.clinic.filter.AppointmentFilter;
import com.medical.clinic.filter.PrescriptionFilter;
import com.medical.clinic.repository.PatientRepository;
import com.medical.clinic.repository.PrescriptionRepository;
import com.medical.clinic.service.PatientService;
import com.medical.clinic.service.PrescriptionService;
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
public class PrescriptionServiceTest {

    @InjectMocks
    PrescriptionService prescriptionService;

    @Mock
    PrescriptionRepository prescriptionRepository;

    @Test
    void test_get_all_prescriptions() {
        var filter = PrescriptionFilter.builder()
                .dateIssued(LocalDate.of(2024, 6, 1))
                .medication("Ibuprofen")
                .dosage("30mg")
                .instruction("One tablet per day")
                .build();

        // Initialize pageable with some sample values
        var pageable = PageRequest.of(0, 10);

        List<PrescriptionEntity> prescriptionEntities = new ArrayList<>();
        // Add some sample appointment entities
        prescriptionEntities.add(new PrescriptionEntity());
        prescriptionEntities.add(new PrescriptionEntity());
        // Create a PageImpl object from the list
        Page<PrescriptionEntity> prescriptionPage = new PageImpl<>(prescriptionEntities);

        // Mock the repository method
        when(prescriptionRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(prescriptionPage);

        // Call the service method
        Page<PrescriptionEntity> result = prescriptionService.getAllPrescriptions(filter, pageable);

        // Assert the result
        assertEquals(prescriptionPage, result);
    }



    @Test
    void test_add_prescription() {

        var prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId(1234);

        var prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setPrescriptionId(1234);
        // Mock the mapper to convert DTO to Entity and back
        when(prescriptionRepository.save(any(PrescriptionEntity.class))).thenReturn(prescriptionEntity);

        // Call the method to test
        PrescriptionDTO result = prescriptionService.addPrescription(prescriptionDTO);

        // Assert the result
        Assertions.assertEquals(prescriptionDTO, result);

    }

    @Test
    public void test_find_by_Id() {
        // Mock the behavior of appointmentRepository.findById
        PrescriptionEntity prescriptionEntity = new PrescriptionEntity(); // Create a dummy AppointmentEntity
        prescriptionEntity.setPrescriptionId(1234); // Set the appointmentId for the dummy entity

        when(prescriptionRepository.findById(anyInt()))
                .thenReturn(Optional.of(prescriptionEntity)); // Return the dummy entity when findById is called

        // Call the method to be tested
        PrescriptionDTO result = prescriptionService.findById(1234);

        // Verify that the correct method was called with the correct argument
        verify(prescriptionRepository).findById(1234);

        // Check if the result is not null
        Assertions.assertNotNull(result);
        // Check if the appointmentId in the result matches the appointmentId of the dummy entity
        Assertions.assertEquals(1234, result.getPrescriptionId());
    }

    @Test
    void test_update_prescription() {

        var prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId(1234);

        var prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setPrescriptionId(1234);

        // Mock the mapper to convert DTO to Entity and back
        when(prescriptionRepository.findById(prescriptionDTO.getPrescriptionId())).thenReturn(Optional.of(prescriptionEntity));
        when(prescriptionRepository.save(any(PrescriptionEntity.class))).thenReturn(prescriptionEntity);


        // Call the method to test
        PrescriptionDTO result = prescriptionService.updatePrescription(prescriptionDTO);

        // Assert the result
        assertEquals(prescriptionDTO, result);
    }



    @Test
    public void test_delete_by_Id() {
        // Arrange
        Integer prescriptionId = 1;
        PrescriptionEntity existingPrescription = new PrescriptionEntity();
        existingPrescription.setPrescriptionId(prescriptionId);

        // Mock behavior
        when(prescriptionRepository.findById(prescriptionId)).thenReturn(java.util.Optional.of(existingPrescription));

        // Act
        prescriptionService.deleteById(prescriptionId);

        // Assert
        verify(prescriptionRepository).findById(prescriptionId);
        verify(prescriptionRepository).setDeleteTrue(prescriptionId);
    }
}
