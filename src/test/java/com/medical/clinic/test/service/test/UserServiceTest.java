package com.medical.clinic.test.service.test;


import com.medical.clinic.dto.UserDTO;
import com.medical.clinic.entity.UserEntity;
import com.medical.clinic.filter.UserFilter;
import com.medical.clinic.mapper.UserMapper;
import com.medical.clinic.repository.UserRepository;
import com.medical.clinic.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Test
    void test_get_all_users() {
        var filter = UserFilter.builder()
                .username("emmajohnoson")
                .password("passw1234")
                .email("emmajohn@gmail.com")
                .contactNumber("01236598")
                .build();

        var pageable = PageRequest.of(0, 10);

        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(new UserEntity());
        userEntities.add(new UserEntity());

        Page<UserEntity> userPage = new PageImpl<>(userEntities);

        when(userRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(userPage);

        Page<UserEntity> result = userService.getAllUsers(filter, pageable);

        assertEquals(userPage, result);
    }



    @Test
    void test_add_user() {

        var userDTO = new UserDTO();
        userDTO.setUserId(1234);
        userDTO.setPatientsDTO(new ArrayList<>());
        userDTO.setDoctorsDTO(new ArrayList<>());
        userDTO.setUserRolesDTO(new ArrayList<>());

        var userEntity = new UserEntity();
        userEntity.setUserId(1234);

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO result = userService.addUser(userDTO);

        Assertions.assertEquals(userDTO, result);

    }


    @Test
    public void test_find_by_Id() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1234);

        when(userRepository.findById(anyInt()))
                .thenReturn(Optional.of(userEntity));

        UserDTO result = userService.findById(userEntity.getUserId());

        Assertions.assertNotNull(result);
    }

    @Test
    void test_update_user() {

        var userDTO = new UserDTO();
        userDTO.setUserId(1234);
        userDTO.setPatientsDTO(new ArrayList<>());
        userDTO.setDoctorsDTO(new ArrayList<>());
        userDTO.setUserRolesDTO(new ArrayList<>());


        var userEntity = new UserEntity();
        userEntity.setUserId(1234);

        when(userRepository.findById(userDTO.getUserId())).thenReturn(Optional.of(userEntity));
        when(userMapper.toEntity(userDTO)).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(userDTO);


        UserDTO result = userService.updateUser(userDTO);

        assertEquals(userDTO, result);
    }


    @Test
    public void test_delete_by_Id() {
        Integer userId = 1;
        UserEntity existingUser = new UserEntity();
        existingUser.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));

        userService.deleteById(userId);
    }
}
