package com.medical.clinic.repository;

import com.medical.clinic.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity,Integer>, JpaSpecificationExecutor<DoctorEntity> {

    @Modifying
    @Query(value = "UPDATE Doctors SET deleted=1 WHERE doctor_Id=:doctorId",nativeQuery = true)
    void setDeleteTrue(Integer doctorId);


}
