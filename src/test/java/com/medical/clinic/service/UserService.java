package com.medical.clinic.service;

import com.medical.clinic.dto.UserDTO;
import com.medical.clinic.entity.UserEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.UserFilter;
import com.medical.clinic.mapper.UserMapper;
import com.medical.clinic.repository.UserRepository;
import com.medical.clinic.specification.UserSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.medical.clinic.mapper.UserMapper.USER_MAPPER;
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;
    public Page<UserEntity> getAllUsers(UserFilter filter,Pageable pageable){
        return  userRepository.findAll(UserSpecification.filters(filter),pageable);
    }

    public UserDTO addUser(UserDTO userDTO) {
        var entity = USER_MAPPER.toEntity(userDTO);


//        List<RoleDTO> userRoleDTOS = userDTO.getUserRolesDTO();
//        if(!userRoleDTOS.isEmpty()){
//            userDTO.setUserRolesDTO(userRoleDTOS);
//        }else {
//            throw new ClassicModelException("Role not found");
//        }
        return USER_MAPPER.toDto(userRepository.save(entity));
    }

    public UserDTO findById(Integer userId) {
        return userRepository.findById(userId)
                .map(USER_MAPPER::toDto).orElse(null);
    }

    public UserDTO updateUser(UserDTO userDTO) {
        var entity = userRepository.findById(userDTO.getUserId())
                .orElseThrow(()-> new ClassicModelException("User with id "+userDTO.getUserId()+" you are trying to update does not exist"));

        var userToUpdate = userMapper.toEntity(userDTO);
        userToUpdate.setPatients(new ArrayList<>());
        userToUpdate.setDoctors(new ArrayList<>());

        if(userToUpdate.getPatients().isEmpty()){
            userToUpdate.setPatients(entity.getPatients());
        }
        if(userToUpdate.getDoctors().isEmpty()){//bje i n dryshem nga null
            userToUpdate.setDoctors(entity.getDoctors());
        }

        return USER_MAPPER.toDto(userRepository.save(userToUpdate));
    }

    @Transactional
    public void deleteById(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ClassicModelException("User with id " + userId + " does not exist"));
        userRepository.setDeleteTrue(userId);
    }
}
