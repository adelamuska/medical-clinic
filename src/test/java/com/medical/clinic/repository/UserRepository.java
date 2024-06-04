package com.medical.clinic.repository;

import com.medical.clinic.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer>, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Modifying
    @Query(value = "UPDATE Users SET deleted=1 WHERE user_Id=:userId",nativeQuery = true)
    void setDeleteTrue(Integer userId);
}
