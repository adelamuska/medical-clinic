package com.medical.clinic.repository;

import com.medical.clinic.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Integer>, JpaSpecificationExecutor<AppointmentEntity> {


    @Modifying
    @Query(value = "UPDATE Appointments SET deleted=1 WHERE appointment_Id=:appointmentId",nativeQuery = true)
    void setDeleteTrue(Integer appointmentId);
}
