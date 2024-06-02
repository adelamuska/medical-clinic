package com.medical.clinic.test.service.test;
import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.AppointmentFilter;
import com.medical.clinic.mapper.AppointmentMapper;
import com.medical.clinic.repository.AppointmentRepository;
import com.medical.clinic.service.AppointmentService;
import com.medical.clinic.specification.AppointmentSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.*;

import static com.medical.clinic.mapper.AppointmentMapper.APPOINTMENT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        // Initialize pageable with some sample values
       var pageable = PageRequest.of(0, 10);

        List<AppointmentEntity> appointmentEntities = new ArrayList<>();
        // Add some sample appointment entities
        appointmentEntities.add(new AppointmentEntity());
        appointmentEntities.add(new AppointmentEntity());
        // Create a PageImpl object from the list
        Page<AppointmentEntity> appointmentPage = new PageImpl<>(appointmentEntities);

        // Mock the repository method
        when(appointmentRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(appointmentPage);

        // Call the service method
        Page<AppointmentEntity> result = appointmentService.getAllAppointments(filter, pageable);

        // Assert the result
        assertEquals(appointmentPage, result);
    }



    @Test
    void test_add_appointment() {

        var appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1234);

        var appointmentEntity = new AppointmentEntity();
        appointmentEntity.setAppointmentId(1234);
        // Mock the mapper to convert DTO to Entity and back
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointmentEntity);

        // Call the method to test
        AppointmentDTO result = appointmentService.addAppointment(appointmentDTO);

        // Assert the result
        Assertions.assertEquals(appointmentDTO, result);

    }


    @Test
    public void test_find_by_Id() {
        // Mock the behavior of appointmentRepository.findById
        AppointmentEntity appointmentEntity = new AppointmentEntity(); // Create a dummy AppointmentEntity
        appointmentEntity.setAppointmentId(1234); // Set the appointmentId for the dummy entity

        when(appointmentRepository.findById(anyInt()))
                .thenReturn(Optional.of(appointmentEntity)); // Return the dummy entity when findById is called

        // Call the method to be tested
        AppointmentDTO result = appointmentService.findById(1234);

        // Verify that the correct method was called with the correct argument
        verify(appointmentRepository).findById(1234);

        // Check if the result is not null
        assertNotNull(result);
        // Check if the appointmentId in the result matches the appointmentId of the dummy entity
        assertEquals(1234, result.getAppointmentId());
    }

    @Test
    void test_update_appointment() {

        var appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1234);

        var appointmentEntity = new AppointmentEntity();
        appointmentEntity.setAppointmentId(1234);
        // Mock repository and mapper interactions
        when(appointmentRepository.findById(appointmentDTO.getAppointmentId())).thenReturn(Optional.of(appointmentEntity));
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointmentEntity);

        // Call the method to test
        AppointmentDTO result = appointmentService.updateAppointment(appointmentDTO);

        // Assert the result
        assertEquals(appointmentDTO, result);
    }


    @Test
    public void test_find_by_Id_not_found() {
        // Mock the behavior of appointmentRepository.findById to return Optional.empty()
        when(appointmentRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        // Call the method to be tested
        AppointmentDTO result = appointmentService.findById(1234);

        // Verify that the correct method was called with the correct argument
        verify(appointmentRepository).findById(1234);

        // Check if the result is null since no appointment was found
        Assertions.assertNull(result);
    }



    @Test
    public void test_update_appointment_not_found() {
        // Create a dummy AppointmentDTO for testing
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1234); // Set an appointmentId

        // Mock the behavior of appointmentRepository.findById to return Optional.empty()
        when(appointmentRepository.findById(anyInt()))
                .thenReturn(java.util.Optional.empty());

        // Call the method to be tested
        Assertions.assertThrows(ClassicModelException.class, () -> appointmentService.updateAppointment(appointmentDTO));

        // Verify that the correct method was called with the correct argument
        verify(appointmentRepository).findById(1234);
    }

    @Test
    public void test_delete_by_Id() {
        Integer appointmentId = 1;
        AppointmentEntity appointmentEntity = new AppointmentEntity();

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointmentEntity));

        Assertions.assertDoesNotThrow(() -> appointmentService.deleteById(appointmentId));
        verify(appointmentRepository).setDeleteTrue(appointmentId);
    }

    @Test
    public void test_delete_by_Id_throws_exception() {
        Integer appointmentId = 1;

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ClassicModelException.class, () -> appointmentService.deleteById(appointmentId));
    }
}