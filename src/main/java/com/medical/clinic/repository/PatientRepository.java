package com.medical.clinic.repository;

import com.medical.clinic.entity.PatientEntity;
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
public interface PatientRepository extends JpaRepository<PatientEntity,Integer>, JpaSpecificationExecutor<PatientEntity> {

    List<PatientEntity> findByFirstName(String firstName, Pageable pageable);
    List<PatientEntity> findByGender(String gender, Pageable pageable);
    List<PatientEntity> findByBirthDate(LocalDate birthDate, Pageable pageable);
    List<PatientEntity> findByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);
    List<PatientEntity> findAllByOrderByLastNameAsc(Pageable pageable);
    List<PatientEntity> findByFirstNameStartingWith(String firstName, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE Patients SET deleted=1 WHERE patient_Id=:patientId",nativeQuery = true)
    void setDeleteTrue(Integer patientId);

}
