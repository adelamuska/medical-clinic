package com.medical.clinic.repository;

import com.medical.clinic.entity.DiagnoseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiagnoseRepository extends JpaRepository<DiagnoseEntity,Integer>, JpaSpecificationExecutor<DiagnoseEntity> {

    List<DiagnoseEntity> findByDiagnosis(String diagnosis, Pageable pageable);
    List<DiagnoseEntity> findByDiagnosisStartingWith(String diagnosis, Pageable pageable);
    List<DiagnoseEntity> findByDateDiagnosedAfter(LocalDate dateDiagnosed, Pageable pageable);
    List<DiagnoseEntity> findByDateDiagnosedBetween(LocalDate fromDate, LocalDate toDate, Pageable pageable);
    List<DiagnoseEntity> findByDiagnosisContaining(String diagnosis, Pageable pageable);
    List<DiagnoseEntity> findByDoctorsDoctorId(Integer doctorId, Pageable pageable);
    Page<DiagnoseEntity> findByPatientDiagnosePatientId(Integer patientId, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE Diagnoses SET deleted=1 WHERE diagnosis_Id=:diagnosisId",nativeQuery = true)
    void setDeleteTrue(Integer diagnosisId);
}


