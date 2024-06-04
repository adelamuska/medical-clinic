package com.medical.clinic.repository;

import com.medical.clinic.entity.DiagnoseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface DiagnoseRepository extends JpaRepository<DiagnoseEntity,Integer>, JpaSpecificationExecutor<DiagnoseEntity> {

    @Modifying
    @Query(value = "UPDATE Diagnoses SET deleted=1 WHERE diagnosis_Id=:diagnosisId",nativeQuery = true)
    void setDeleteTrue(Integer diagnosisId);
}


