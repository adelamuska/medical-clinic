package com.medical.clinic.test.service.test;

import com.medical.clinic.dto.DiagnoseDTO;
import com.medical.clinic.entity.DiagnoseEntity;
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


        var pageable = PageRequest.of(0, 10);

        List<DiagnoseEntity> diagnoseEntities = new ArrayList<>();

        diagnoseEntities.add(new DiagnoseEntity());
        diagnoseEntities.add(new DiagnoseEntity());

        Page<DiagnoseEntity> diagnosePage = new PageImpl<>(diagnoseEntities);


        when(diagnoseRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(diagnosePage);

        Page<DiagnoseEntity> result = diagnoseService.getAllDiagnoses(filter, pageable);

        assertEquals(diagnosePage, result);
    }

    @Test
    void test_add_diagnose() {

        var diagnoseDTO = new DiagnoseDTO();
        diagnoseDTO.setDiagnosisId(1234);

        var diagnoseEntity = new DiagnoseEntity();
        diagnoseEntity.setDiagnosisId(1234);

        when(diagnoseRepository.save(any(DiagnoseEntity.class))).thenReturn(diagnoseEntity);

        DiagnoseDTO result = diagnoseService.addDiagnose(diagnoseDTO);

        Assertions.assertEquals(diagnoseDTO, result);

    }


    @Test
    public void test_find_by_Id() {

        DiagnoseEntity diagnoseEntity = new DiagnoseEntity();
        diagnoseEntity.setDiagnosisId(1234);

        when(diagnoseRepository.findById(anyInt()))
                .thenReturn(Optional.of(diagnoseEntity));

        DiagnoseDTO result = diagnoseService.findById(diagnoseEntity.getDiagnosisId());

        Assertions.assertNotNull(result);

    }

    @Test
    void test_update_diagnose() {

        var diagnoseDTO = new DiagnoseDTO();
        diagnoseDTO.setDiagnosisId(1234);

        var diagnoseEntity = new DiagnoseEntity();
        diagnoseEntity.setDiagnosisId(1234);

        when(diagnoseRepository.findById(diagnoseDTO.getDiagnosisId())).thenReturn(Optional.of(diagnoseEntity));
        when(diagnoseRepository.save(any(DiagnoseEntity.class))).thenReturn(diagnoseEntity);

        DiagnoseDTO result = diagnoseService.updateDiagnose(diagnoseDTO);

        assertEquals(diagnoseDTO, result);
    }


    @Test
    public void test_delete_by_Id() {
        Integer existingDiagnoseId = 1;
        DiagnoseEntity existingDiagnose = new DiagnoseEntity();
        existingDiagnose.setDiagnosisId(existingDiagnoseId);

        when(diagnoseRepository.findById(existingDiagnoseId)).thenReturn(java.util.Optional.of(existingDiagnose));

        diagnoseService.deleteById(existingDiagnoseId);

    }


}
