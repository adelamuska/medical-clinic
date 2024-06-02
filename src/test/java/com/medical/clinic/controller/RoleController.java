package com.medical.clinic.controller;

import com.medical.clinic.dto.RoleDTO;
import com.medical.clinic.entity.RoleEntity;
import com.medical.clinic.filter.RoleFilter;
import com.medical.clinic.mapper.PageDTO;
import com.medical.clinic.service.RoleService;
import com.medical.clinic.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.medical.clinic.mapper.RoleMapper.ROLE_MAPPER;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity<PageDTO<RoleDTO>> getRoles(@RequestParam(required = false,defaultValue = "0") Integer page,
                                                     @RequestParam(required = false,defaultValue = "5") Integer size,
                                                     @RequestParam(required = false) String roleName,
                                                     @RequestParam(required = false) String sortBy,
                                                     @RequestParam(required = false) String order){

        RoleFilter filter= RoleFilter.builder()
                .roleName(roleName)
                .sortBy(sortBy)
                .order(order)
                .build();

        var pageable = PageRequest.of(page,size);
        Page<RoleEntity> rolesPage = roleService.getAllRoles(filter,pageable);
        return ResponseEntity.ok(PageUtils.toPageImpl(rolesPage,ROLE_MAPPER));

    }

    @PostMapping
    public ResponseEntity<RoleDTO> addRole(@RequestBody RoleDTO roleDTO){
        return ResponseEntity.ok(roleService.addRole(roleDTO));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> findById(@PathVariable Integer roleId){
        var role = roleService.findById(roleId);
        return role!=null?ResponseEntity.ok(role):ResponseEntity.notFound().build();
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Integer roleId,@RequestBody RoleDTO roleDTO){
        roleDTO.setRoleId(roleId);
        return ResponseEntity.ok(roleService.updateRole(roleDTO));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer roleId){
        roleService.deleteById(roleId);
        return ResponseEntity.ok().build();
    }

}
