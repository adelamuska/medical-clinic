package com.medical.clinic.test.controller.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.dto.DiagnoseDTO;
import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.entity.DiagnoseEntity;
import com.medical.clinic.filter.AppointmentFilter;
import com.medical.clinic.filter.DiagnoseFilter;
import com.medical.clinic.repository.AppointmentRepository;
import com.medical.clinic.repository.DiagnoseRepository;
import com.medical.clinic.service.AppointmentService;
import com.medical.clinic.service.DiagnoseService;
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
public class DiagnoseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DiagnoseRepository diagnoseRepository;

    @MockBean
    DiagnoseService diagnoseService;

    @WithMockUser
    @Test
    void test_get_diagnoses() throws Exception {
        var diagnoseEntity = DiagnoseEntity.builder().diagnosisId(1234).build();
        Page<DiagnoseEntity> diagnosePage = new PageImpl<>(Collections.singletonList(diagnoseEntity));
        when(diagnoseService.getAllDiagnoses(any(DiagnoseFilter.class), any(PageRequest.class)))
                .thenReturn(diagnosePage);

        mockMvc.perform(get("/api/v1/diagnoses")
                        .param("page", "0")
                        .param("size", "5")
                        .param("dateDiagnosed", LocalDate.now().toString())
                        .param("diagnosis", "checkup")
                        .param("status", "pending")
                        .param("sortBy", "dateDiagnosed")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].diagnosisId").value(1234));
    }


    @WithMockUser
    @Test
    void test_add_diagnose() throws Exception {
        var diagnoseDTO = new DiagnoseDTO();
        diagnoseDTO.setDiagnosisId(1234);
        // Mock the service method
        when(diagnoseService.addDiagnose(any(DiagnoseDTO.class))).thenReturn(diagnoseDTO);

        // Perform the POST request
        mockMvc.perform(post("/api/v1/diagnoses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(diagnoseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnosisId").value(1234));
    }

    @Test
    @WithMockUser
    void test_find_by_Id() throws Exception {

        var diagnoseDTO = new DiagnoseDTO();
        diagnoseDTO.setDiagnosisId(1234);
        when(diagnoseService.findById(1234)).thenReturn(diagnoseDTO);

        mockMvc.perform(get("/api/v1/diagnoses/1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnosisId").value(1234));
    }


    @Test
    @WithMockUser
    void test_update_diagnose() throws Exception {
        var diagnoseDTO = new DiagnoseDTO();
        diagnoseDTO.setDiagnosisId(1234);

        when(diagnoseService.updateDiagnose(any(DiagnoseDTO.class))).thenReturn(diagnoseDTO);

        mockMvc.perform(put("/api/v1/diagnoses/1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(diagnoseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnosisId").value(1234));
    }

    @Test
    @WithMockUser
    void test_delete_by_Id() throws Exception {
        mockMvc.perform(delete("/api/v1/diagnoses/1234")
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
