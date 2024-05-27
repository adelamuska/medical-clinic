package com.medical.clinic.mapper;

import com.medical.clinic.dto.RoleDTO;
import com.medical.clinic.dto.UserDTO;
import com.medical.clinic.dto.UserRoleDTO;
import com.medical.clinic.entity.RoleEntity;
import com.medical.clinic.entity.UserEntity;
import com.medical.clinic.entity.UserRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import static com.medical.clinic.mapper.DoctorMapper.USER_MAPPER;


@Mapper(componentModel = "spring")
public abstract class RoleMapper implements BaseMapper<RoleEntity, RoleDTO> {

    public static RoleMapper ROLE_MAPPER= Mappers.getMapper(RoleMapper.class);


    //@Mapping(source = "roleUsers", target = "roleUsersDTO",qualifiedByName = "mapRoleUsersDTO")
    public abstract RoleDTO toDto(RoleEntity roleEntity);

    //@Mapping(source = "roleUsersDTO", target = "roleUsers")
    public abstract RoleEntity toEntity(RoleDTO roleDTO);

    @Named("mapRoleUsersDTO")
    public UserDTO mapUserRole(UserEntity entity){
        if(entity != null)
            return USER_MAPPER.toDto(entity);
        return null;
    }
}
