package com.medical.clinic.test.service.test;

import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.dto.PrescriptionDTO;
import com.medical.clinic.dto.UserDTO;
import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.entity.PrescriptionEntity;
import com.medical.clinic.entity.UserEntity;
import com.medical.clinic.filter.AppointmentFilter;
import com.medical.clinic.filter.UserFilter;
import com.medical.clinic.mapper.UserMapper;
import com.medical.clinic.repository.PrescriptionRepository;
import com.medical.clinic.repository.UserRepository;
import com.medical.clinic.service.PrescriptionService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
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

        // Initialize pageable with some sample values
        var pageable = PageRequest.of(0, 10);

        List<UserEntity> userEntities = new ArrayList<>();
        // Add some sample appointment entities
        userEntities.add(new UserEntity());
        userEntities.add(new UserEntity());
        // Create a PageImpl object from the list
        Page<UserEntity> userPage = new PageImpl<>(userEntities);

        // Mock the repository method
        when(userRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(userPage);

        // Call the service method
        Page<UserEntity> result = userService.getAllUsers(filter, pageable);

        // Assert the result
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

        // Mock the mapper to convert DTO to Entity and back
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // Call the method to test
        UserDTO result = userService.addUser(userDTO);

        // Assert the result
        Assertions.assertEquals(userDTO, result);

    }


    @Test
    public void test_find_by_Id() {
        // Mock the behavior of appointmentRepository.findById
        UserEntity userEntity = new UserEntity(); // Create a dummy AppointmentEntity
        userEntity.setUserId(1234); // Set the appointmentId for the dummy entity

        when(userRepository.findById(anyInt()))
                .thenReturn(Optional.of(userEntity)); // Return the dummy entity when findById is called

        // Call the method to be tested
        UserDTO result = userService.findById(1234);

        // Verify that the correct method was called with the correct argument
        verify(userRepository).findById(1234);

        // Check if the result is not null
        Assertions.assertNotNull(result);
        // Check if the appointmentId in the result matches the appointmentId of the dummy entity
        Assertions.assertEquals(1234, result.getUserId());
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

        // Mock the mapper to convert DTO to Entity and back
        when(userRepository.findById(userDTO.getUserId())).thenReturn(Optional.of(userEntity));
        when(userMapper.toEntity(userDTO)).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(userDTO);


        // Call the method to test
        UserDTO result = userService.updateUser(userDTO);

        // Assert the result
        assertEquals(userDTO, result);
    }


    @Test
    public void test_delete_by_Id() {
        // Arrange
        Integer userId = 1;
        UserEntity existingUser = new UserEntity();
        existingUser.setUserId(userId);

        // Mock behavior
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));

        // Act
        userService.deleteById(userId);

        // Assert
        verify(userRepository).findById(userId);
        verify(userRepository).setDeleteTrue(userId);
    }
}
