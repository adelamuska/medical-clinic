package com.medical.clinic.repository;

import com.medical.clinic.entity.AppointmentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Integer>, JpaSpecificationExecutor<AppointmentEntity> {

    List<AppointmentEntity> findAllByAppointmentDate(LocalDate appointmentDate, Pageable pageable);
    List<AppointmentEntity> findByAppointmentDateAndStatus(LocalDate appointmentDate,String status, Pageable pageable);
    List<AppointmentEntity> findByAppointmentDateAndStartTimeBetween(LocalDate appointmentDate, LocalTime startTime, LocalTime endTime, Pageable pageable);

    List<AppointmentEntity> findByDoctorsDoctorId(Integer doctorId, Pageable pageable);
    @Query("SELECT a FROM AppointmentEntity a WHERE a.doctors.doctorId = :doctorId AND a.appointmentDate BETWEEN :startDate AND :endDate")
    List<AppointmentEntity> findAppointmentsByDoctorAndDateRange(Integer doctorId, LocalDate startDate, LocalDate endDate);

    @Modifying
    @Query(value = "UPDATE Appointments SET deleted=1 WHERE appointment_Id=:appointmentId",nativeQuery = true)
    void setDeleteTrue(Integer appointmentId);
}
