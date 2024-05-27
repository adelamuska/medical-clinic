package com.medical.clinic.service;

import com.medical.clinic.dto.RoleDTO;
import com.medical.clinic.entity.RoleEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.RoleFilter;
import com.medical.clinic.specification.RoleSpecification;
import com.medical.clinic.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.medical.clinic.mapper.RoleMapper.ROLE_MAPPER;
@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    public Page<RoleEntity> getAllRoles(RoleFilter filter,Pageable pageable){
        return roleRepository.findAll(RoleSpecification.filters(filter),pageable);
    }

    public RoleDTO addRole(RoleDTO roleDTO) {
        var entity = ROLE_MAPPER.toEntity(roleDTO);
        return ROLE_MAPPER.toDto(roleRepository.save(entity));
    }

    public RoleDTO findById(Integer roleId) {
        return roleRepository.findById(roleId)
                .map(ROLE_MAPPER::toDto).orElse(null);
    }

    public RoleDTO updateRole(RoleDTO roleDTO) {
        var entity = roleRepository.findById(roleDTO.getRoleId())
                .orElseThrow(()-> new ClassicModelException("Role with id "+roleDTO.getRoleId()+" you are trying to update does not exist"));
        entity = ROLE_MAPPER.toEntity(roleDTO);
        return ROLE_MAPPER.toDto(roleRepository.save(entity));
    }

    @Transactional
//    public void deleteById(Integer roleId){
//        var roleFound = roleRepository.findById(roleId)
//                .orElseThrow(() -> new ClassicModelException("Role with id " + roleId + " does not exist"));
//        roleRepository.delete(roleFound);
//    }

    public void deleteById(Integer roleId) {
        roleRepository.findById(roleId)
                .orElseThrow(() -> new ClassicModelException("Role with id " + roleId + " does not exist"));
        roleRepository.setDeleteTrue(roleId);
    }
}
