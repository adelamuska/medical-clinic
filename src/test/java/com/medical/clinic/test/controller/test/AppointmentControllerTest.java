package com.medical.clinic.test.controller.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.filter.AppointmentFilter;
import com.medical.clinic.repository.AppointmentRepository;
import com.medical.clinic.service.AppointmentService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class AppointmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AppointmentRepository appointmentRepository;

    @MockBean
    AppointmentService appointmentService;

    @WithMockUser
    @Test
    void test_get_appointments() throws Exception {
        var appointmentEntity = AppointmentEntity.builder().appointmentId(1234).build();
        Page<AppointmentEntity> appointmentPage = new PageImpl<>(Collections.singletonList(appointmentEntity));
        when(appointmentService.getAllAppointments(any(AppointmentFilter.class), any(PageRequest.class)))
                .thenReturn(appointmentPage);

        mockMvc.perform(get("/api/v1/appointments")
                        .param("page", "0")
                        .param("size", "5")
                        .param("appointmentDate", LocalDate.now().toString())
                        .param("description", "checkup")
                        .param("status", "pending")
                        .param("sortBy", "appointmentDate")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].appointmentId").value(1234));
    }


    @WithMockUser
    @Test
    void test_add_appointment() throws Exception {
        var appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1234);

        when(appointmentService.addAppointment(any(AppointmentDTO.class))).thenReturn(appointmentDTO);

        mockMvc.perform(post("/api/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(appointmentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentId").value(1234));
    }

    @Test
    @WithMockUser
    void test_find_by_Id() throws Exception {

        var appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1234);
        when(appointmentService.findById(1234)).thenReturn(appointmentDTO);

        mockMvc.perform(get("/api/v1/appointments/1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentId").value(1234));
    }


    @Test
    @WithMockUser
    void test_update_appointment() throws Exception {
        var appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1234);

        when(appointmentService.updateAppointment(any(AppointmentDTO.class))).thenReturn(appointmentDTO);

        mockMvc.perform(put("/api/v1/appointments/1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(appointmentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentId").value(1234));
    }

    @Test
    @WithMockUser
    void test_delete_by_Id() throws Exception {
        mockMvc.perform(delete("/api/v1/appointments/1234")
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
