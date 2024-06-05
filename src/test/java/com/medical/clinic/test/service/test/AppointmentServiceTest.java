package com.medical.clinic.test.service.test;

import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.filter.AppointmentFilter;
import com.medical.clinic.repository.AppointmentRepository;
import com.medical.clinic.service.AppointmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
    @InjectMocks
    AppointmentService appointmentService;

    @Mock
    AppointmentRepository appointmentRepository;


    @Test
    void test_get_all_appointments() {
       var filter = AppointmentFilter.builder()
                .appointmentDate(LocalDate.of(2024, 6, 1))
                .description("Sample Description")
                .status("Scheduled")
                .build();

       var pageable = PageRequest.of(0, 10);

        List<AppointmentEntity> appointmentEntities = new ArrayList<>();
        appointmentEntities.add(new AppointmentEntity());
        appointmentEntities.add(new AppointmentEntity());

        Page<AppointmentEntity> appointmentPage = new PageImpl<>(appointmentEntities);

        when(appointmentRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(appointmentPage);

        Page<AppointmentEntity> result = appointmentService.getAllAppointments(filter, pageable);

        assertEquals(appointmentPage, result);
    }



    @Test
    void test_add_appointment() {

        var appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1234);

        var appointmentEntity = new AppointmentEntity();
        appointmentEntity.setAppointmentId(1234);

        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointmentEntity);

        AppointmentDTO result = appointmentService.addAppointment(appointmentDTO);

        Assertions.assertEquals(appointmentDTO, result);

    }


    @Test
    public void test_find_by_Id() {
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setAppointmentId(1234);

            when(appointmentRepository.findById(anyInt()))
                    .thenReturn(Optional.of(appointmentEntity));

            AppointmentDTO result = appointmentService.findById(appointmentEntity.getAppointmentId());

            Assertions.assertNotNull(result);

        }

    @Test
    void test_update_appointment() {

        var appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1234);

        var appointmentEntity = new AppointmentEntity();
        appointmentEntity.setAppointmentId(1234);

        when(appointmentRepository.findById(appointmentDTO.getAppointmentId())).thenReturn(Optional.of(appointmentEntity));
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointmentEntity);

        AppointmentDTO result = appointmentService.updateAppointment(appointmentDTO);

        assertEquals(appointmentDTO, result);
    }

    @Test
    public void test_delete_by_Id() {
        Integer appointmentId = 1;
        AppointmentEntity existingAppointment = new AppointmentEntity();
        existingAppointment.setAppointmentId(appointmentId);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));

        appointmentService.deleteById(appointmentId);
    }
}