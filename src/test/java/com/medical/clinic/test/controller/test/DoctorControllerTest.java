package com.medical.clinic.test.controller.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.clinic.dto.DoctorDTO;
import com.medical.clinic.entity.DoctorEntity;
import com.medical.clinic.filter.DoctorFilter;
import com.medical.clinic.repository.DoctorRepository;
import com.medical.clinic.service.DoctorService;
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

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class DoctorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DoctorRepository doctorRepository;

    @MockBean
    DoctorService doctorService;

    @WithMockUser
    @Test
    void test_get_doctors() throws Exception {
        var doctorEntity = DoctorEntity.builder().doctorId(1234).build();
        Page<DoctorEntity> doctorPage = new PageImpl<>(Collections.singletonList(doctorEntity));
        when(doctorService.getAllDoctors(any(DoctorFilter.class), any(PageRequest.class)))
                .thenReturn(doctorPage);

        mockMvc.perform(get("/api/v1/doctors")
                        .param("page", "0")
                        .param("size", "5")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("specialization", "Surgeon")
                        .param("sortBy", "firstName")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].doctorId").value(1234));
    }


    @WithMockUser
    @Test
    void test_add_doctor() throws Exception {
        var doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(1234);

        when(doctorService.addDoctor(any(DoctorDTO.class))).thenReturn(doctorDTO);

        mockMvc.perform(post("/api/v1/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(doctorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorId").value(1234));
    }

    @Test
    @WithMockUser
    void test_find_by_Id() throws Exception {

        var doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(1234);
        when(doctorService.findById(1234)).thenReturn(doctorDTO);

        mockMvc.perform(get("/api/v1/doctors/1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorId").value(1234));
    }


    @Test
    @WithMockUser
    void test_update_doctor() throws Exception {
        var doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(1234);

        when(doctorService.updateDoctor(any(DoctorDTO.class))).thenReturn(doctorDTO);

        mockMvc.perform(put("/api/v1/doctors/1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(doctorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorId").value(1234));
    }

    @Test
    @WithMockUser
    void test_delete_by_Id() throws Exception {
        mockMvc.perform(delete("/api/v1/doctors/1234")
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
