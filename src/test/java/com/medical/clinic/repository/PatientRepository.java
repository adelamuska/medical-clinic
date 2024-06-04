package com.medical.clinic.repository;

import com.medical.clinic.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepository extends JpaRepository<PatientEntity,Integer>, JpaSpecificationExecutor<PatientEntity> {

    @Modifying
    @Query(value = "UPDATE Patients SET deleted=1 WHERE patient_Id=:patientId",nativeQuery = true)
    void setDeleteTrue(Integer patientId);

}
