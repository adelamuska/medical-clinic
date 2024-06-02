package com.medical.clinic.test;

import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.filter.AppointmentFilter;
import com.medical.clinic.specification.AppointmentSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpTest {

    @Test
    void test_appointment_specification() {
        // Arrange
        AppointmentFilter filter = new AppointmentFilter();
        filter.setAppointmentDate(LocalDate.of(2024,06,01));
        filter.setDescription("Test description");
        filter.setStatus("Scheduled");
        filter.setSortBy("appointmentDate");
        filter.setOrder("ASC");

        // Act
        Specification<AppointmentEntity> specification = AppointmentSpecification.filters(filter);

        // Assert
        assertNotNull(specification);
    }
}
