package com.medical.clinic.repository;

import com.medical.clinic.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity,Integer>, JpaSpecificationExecutor<PrescriptionEntity> {


    @Modifying
    @Query(value = "UPDATE Prescriptions SET deleted=1 WHERE prescription_Id=:prescriptionId",nativeQuery = true)
    void setDeleteTrue(Integer prescriptionId);


}
