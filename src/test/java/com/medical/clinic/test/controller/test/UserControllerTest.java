package com.medical.clinic.test.controller.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.clinic.dto.UserDTO;
import com.medical.clinic.entity.UserEntity;
import com.medical.clinic.filter.UserFilter;
import com.medical.clinic.repository.UserRepository;
import com.medical.clinic.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserService userService;

    @WithMockUser
    @Test
    void test_get_users() throws Exception {
        var userEntity = UserEntity.builder().userId(1234).build();
        Page<UserEntity> userPage = new PageImpl<>(Collections.singletonList(userEntity));
        when(userService.getAllUsers(any(UserFilter.class), any(PageRequest.class)))
                .thenReturn(userPage);

        mockMvc.perform(get("/api/v1/users")
                        .param("page", "0")
                        .param("size", "5")
                        .param("username", "johndoe")
                        .param("password", "password123")
                        .param("email", "johndoe@example.com")
                        .param("contactNumber", "1234567890")
                        .param("sortBy", "username")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].userId").value(1234));
    }


    @WithMockUser
    @Test
    void test_add_user() throws Exception {
        var userDTO = new UserDTO();
        userDTO.setUserId(1234);

        when(userService.addUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1234));
    }

    @Test
    @WithMockUser
    void test_find_by_Id() throws Exception {

        var userDTO = new UserDTO();
        userDTO.setUserId(1234);
        when(userService.findById(1234)).thenReturn(userDTO);

        mockMvc.perform(get("/api/v1/users/1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1234));
    }


    @Test
    @WithMockUser
    void test_update_user() throws Exception {
        var userDTO = new UserDTO();
        userDTO.setUserId(1234);

        when(userService.updateUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(put("/api/v1/users/1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1234));
    }

    @Test
    @WithMockUser
    void test_delete_by_Id() throws Exception {
        mockMvc.perform(delete("/api/v1/users/1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
