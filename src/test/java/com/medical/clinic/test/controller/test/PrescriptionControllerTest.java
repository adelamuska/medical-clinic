package com.medical.clinic.test.controller.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.clinic.dto.PatientDTO;
import com.medical.clinic.dto.PrescriptionDTO;
import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.entity.PrescriptionEntity;
import com.medical.clinic.filter.PatientFilter;
import com.medical.clinic.filter.PrescriptionFilter;
import com.medical.clinic.repository.PatientRepository;
import com.medical.clinic.repository.PrescriptionRepository;
import com.medical.clinic.service.PatientService;
import com.medical.clinic.service.PrescriptionService;
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
public class PrescriptionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PrescriptionRepository prescriptionRepository;

    @MockBean
    PrescriptionService prescriptionService;

    @WithMockUser
    @Test
    void test_get_prescriptions() throws Exception {
        var prescriptionEntity = PrescriptionEntity.builder().prescriptionId(1234).build();
        Page<PrescriptionEntity> prescriptionPage = new PageImpl<>(Collections.singletonList(prescriptionEntity));
        when(prescriptionService.getAllPrescriptions(any(PrescriptionFilter.class), any(PageRequest.class)))
                .thenReturn(prescriptionPage);

        mockMvc.perform(get("/api/v1/prescriptions")
                        .param("page", "0")
                        .param("size", "5")
                        .param("dateIssued", LocalDate.now().toString())
                        .param("medication", "Medicine A")
                        .param("dosage", "10mg")
                        .param("instruction", "Take with food")
                        .param("sortBy", "dateIssued")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].prescriptionId").value(1234));
    }


    @WithMockUser
    @Test
    void test_add_prescription() throws Exception {
        var prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId(1234);
        // Mock the service method
        when(prescriptionService.addPrescription(any(PrescriptionDTO.class))).thenReturn(prescriptionDTO);

        // Perform the POST request
        mockMvc.perform(post("/api/v1/prescriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(prescriptionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prescriptionId").value(1234));
    }

    @Test
    @WithMockUser
    void test_find_by_Id() throws Exception {

        var prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId(1234);
        when(prescriptionService.findById(1234)).thenReturn(prescriptionDTO);

        mockMvc.perform(get("/api/v1/prescriptions/1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prescriptionId").value(1234));
    }


    @Test
    @WithMockUser
    void test_update_prescription() throws Exception {
        var prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId(1234);

        when(prescriptionService.updatePrescription(any(PrescriptionDTO.class))).thenReturn(prescriptionDTO);

        mockMvc.perform(put("/api/v1/prescriptions/1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(prescriptionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prescriptionId").value(1234));
    }

    @Test
    @WithMockUser
    void test_delete_by_Id() throws Exception {
        mockMvc.perform(delete("/api/v1/prescriptions/1234")
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
