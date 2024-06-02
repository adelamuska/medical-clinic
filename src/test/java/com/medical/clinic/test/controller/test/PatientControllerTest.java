package com.medical.clinic.test.controller.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.clinic.dto.PatientDTO;
import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.filter.PatientFilter;
import com.medical.clinic.repository.PatientRepository;
import com.medical.clinic.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientRepository patientRepository;

    @MockBean
    PatientService patientService;

    @WithMockUser
    @Test
    void test_get_patients() throws Exception {
        var patientEntity = PatientEntity.builder().patientId(1234).build();
        Page<PatientEntity> patientPage = new PageImpl<>(Collections.singletonList(patientEntity));
        when(patientService.getAllPatients(any(PatientFilter.class), any(PageRequest.class)))
                .thenReturn(patientPage);

        mockMvc.perform(get("/api/v1/patients")
                        .param("page", "0")
                        .param("size", "5")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("birthDate", LocalDate.now().toString())
                        .param("gender", "male")
                        .param("address", "123 Main St")
                        .param("sortBy", "firstName")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].patientId").value(1234));
    }


    @WithMockUser
    @Test
    void test_add_patient() throws Exception {
        var patientDTO = new PatientDTO();
        patientDTO.setPatientId(1234);

        when(patientService.addPatient(any(PatientDTO.class))).thenReturn(patientDTO);

        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value(1234));
    }

    @Test
    @WithMockUser
    void test_find_by_Id() throws Exception {

        var patientDTO = new PatientDTO();
        patientDTO.setPatientId(1234);
        when(patientService.findById(1234)).thenReturn(patientDTO);

        mockMvc.perform(get("/api/v1/patients/1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value(1234));
    }


    @Test
    @WithMockUser
    void test_update_patient() throws Exception {
        var patientDTO = new PatientDTO();
        patientDTO.setPatientId(1234);

        when(patientService.updatePatient(any(PatientDTO.class))).thenReturn(patientDTO);

        mockMvc.perform(put("/api/v1/patients/1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value(1234));
    }

    @Test
    @WithMockUser
    void test_delete_by_Id() throws Exception {
        mockMvc.perform(delete("/api/v1/patients/1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
