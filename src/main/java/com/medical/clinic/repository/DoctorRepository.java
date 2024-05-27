package com.medical.clinic.repository;

import com.medical.clinic.entity.DoctorEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity,Integer>, JpaSpecificationExecutor<DoctorEntity> {

    List<DoctorEntity> findAllByFirstName(String name);
    List<DoctorEntity> findAllByFirstNameAndLastName(String firstName,String lastName, Pageable pageable);
    List<DoctorEntity> findAllByFirstNameAndSpecialization(String name, String specialization, Pageable pageable);
    List<DoctorEntity> findBySpecialization(String specialization, Pageable pageable);
    List<DoctorEntity> findByUsersUserId(Integer userId, Pageable pageable);
    @Query("SELECT d.doctors FROM DiagnoseEntity d WHERE d.diagnosis = :diagnosis")
    DoctorEntity findDoctorByDiagnosisId(String diagnosis);

    @Modifying
    @Query(value = "UPDATE Doctors SET deleted=1 WHERE doctor_Id=:doctorId",nativeQuery = true)
    void setDeleteTrue(Integer doctorId);


}
