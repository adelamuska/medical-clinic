package com.medical.clinic.repository;

import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.entity.PrescriptionEntity;
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
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity,Integer>, JpaSpecificationExecutor<PrescriptionEntity> {

    List<PrescriptionEntity> findByMedication(String medication, Pageable pageable);
    List<PrescriptionEntity> findByDateIssued(LocalDate dateIssued, Pageable pageable);
    List<PrescriptionEntity> findByDateIssuedBefore(LocalDate dateIssued, Pageable pageable);
    List<PrescriptionEntity> findByMedicationAndDosage(String medication, String dosage, Pageable pageable);
    List<PrescriptionEntity> findAllByOrderByDateIssuedDesc(Pageable pageable);
    List<PrescriptionEntity> findByInstructionContaining(String instruction, Pageable pageable);
    List<PrescriptionEntity> findByDoctorsDoctorId(Integer doctorId, Pageable pageable);
    List<PrescriptionEntity> findByPatientsPatientId(Integer patientId, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE Prescriptions SET deleted=1 WHERE prescription_Id=:prescriptionId",nativeQuery = true)
    void setDeleteTrue(Integer prescriptionId);


}
