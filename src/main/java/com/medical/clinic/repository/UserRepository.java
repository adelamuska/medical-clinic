package com.medical.clinic.repository;

import com.medical.clinic.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer>, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByUsername(String username);
//    List<UserEntity> findByEmail(String email, Pageable pageable);
//    List<UserEntity> findByEmailStartingWith(String email, Pageable pageable);
//    List<UserEntity> findByUsernameOrEmail(String username,String email, Pageable pageable);
//    List<UserEntity> findByContactNumberEndingWith(String contactNumber, Pageable pageable);
//    List<UserEntity> findByContactNumber(String contactNumber, Pageable pageable);
//
    @Modifying
    @Query(value = "UPDATE Users SET deleted=1 WHERE user_Id=:userId",nativeQuery = true)
    void setDeleteTrue(Integer userId);
}
