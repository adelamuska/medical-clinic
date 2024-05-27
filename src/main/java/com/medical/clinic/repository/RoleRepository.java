package com.medical.clinic.repository;

import com.medical.clinic.entity.RoleEntity;
import com.medical.clinic.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Integer>, JpaSpecificationExecutor<RoleEntity> {

    List<RoleEntity> findByRoleName(String roleName, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE Roles SET deleted=1 WHERE role_Id=:roleId",nativeQuery = true)
    void setDeleteTrue(Integer roleId);

}
