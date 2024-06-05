package com.medical.clinic.test.service.test;

import com.medical.clinic.dto.PatientDTO;
import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.filter.PatientFilter;
import com.medical.clinic.mapper.PatientMapper;
import com.medical.clinic.repository.PatientRepository;
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

        var pageable = PageRequest.of(0, 10);

        List<PatientEntity> patientEntities = new ArrayList<>();
        patientEntities.add(new PatientEntity());
        patientEntities.add(new PatientEntity());
        Page<PatientEntity> patientPage = new PageImpl<>(patientEntities);

        when(patientRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(patientPage);

        Page<PatientEntity> result = patientService.getAllPatients(filter, pageable);

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

        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patientEntity);

        PatientDTO result = patientService.addPatient(patientDTO);

        Assertions.assertEquals(patientDTO, result);

    }

    @Test
    public void test_find_by_Id() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setPatientId(1234);

        when(patientRepository.findById(anyInt()))
                .thenReturn(Optional.of(patientEntity));

        PatientDTO result = patientService.findById(patientEntity.getPatientId());

        Assertions.assertNotNull(result);

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

        when(patientRepository.findById(patientDTO.getPatientId())).thenReturn(Optional.of(patientEntity));
        when(patientMapper.toEntity(patientDTO)).thenReturn(patientEntity);
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patientEntity);
        when(patientMapper.toDto(patientEntity)).thenReturn(patientDTO);


        PatientDTO result = patientService.updatePatient(patientDTO);

        assertEquals(patientDTO, result);
    }

    @Test
    public void test_delete_by_Id() {
        Integer patientId = 1;
        PatientEntity existingPatient = new PatientEntity();
        existingPatient.setPatientId(patientId);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));


        patientService.deleteById(patientId);

    }
}
