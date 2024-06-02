package com.medical.clinic.test.service.test;

import com.medical.clinic.dto.PrescriptionDTO;
import com.medical.clinic.entity.PrescriptionEntity;
import com.medical.clinic.filter.PrescriptionFilter;
import com.medical.clinic.repository.PrescriptionRepository;
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

        var pageable = PageRequest.of(0, 10);

        List<PrescriptionEntity> prescriptionEntities = new ArrayList<>();
        prescriptionEntities.add(new PrescriptionEntity());
        prescriptionEntities.add(new PrescriptionEntity());

        Page<PrescriptionEntity> prescriptionPage = new PageImpl<>(prescriptionEntities);

        when(prescriptionRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(prescriptionPage);

        Page<PrescriptionEntity> result = prescriptionService.getAllPrescriptions(filter, pageable);

        assertEquals(prescriptionPage, result);
    }



    @Test
    void test_add_prescription() {

        var prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId(1234);

        var prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setPrescriptionId(1234);

        when(prescriptionRepository.save(any(PrescriptionEntity.class))).thenReturn(prescriptionEntity);

        PrescriptionDTO result = prescriptionService.addPrescription(prescriptionDTO);

        Assertions.assertEquals(prescriptionDTO, result);

    }

    @Test
    public void test_find_by_Id() {
        PrescriptionEntity prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setPrescriptionId(1234);

        when(prescriptionRepository.findById(anyInt()))
                .thenReturn(Optional.of(prescriptionEntity));

        PrescriptionDTO result = prescriptionService.findById(prescriptionEntity.getPrescriptionId());

        Assertions.assertNotNull(result);
    }

    @Test
    void test_update_prescription() {

        var prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId(1234);

        var prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setPrescriptionId(1234);

        when(prescriptionRepository.findById(prescriptionDTO.getPrescriptionId())).thenReturn(Optional.of(prescriptionEntity));
        when(prescriptionRepository.save(any(PrescriptionEntity.class))).thenReturn(prescriptionEntity);


        PrescriptionDTO result = prescriptionService.updatePrescription(prescriptionDTO);

        assertEquals(prescriptionDTO, result);
    }



    @Test
    public void test_delete_by_Id() {

        Integer prescriptionId = 1;
        PrescriptionEntity existingPrescription = new PrescriptionEntity();
        existingPrescription.setPrescriptionId(prescriptionId);

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(java.util.Optional.of(existingPrescription));

        prescriptionService.deleteById(prescriptionId);
    }
}
