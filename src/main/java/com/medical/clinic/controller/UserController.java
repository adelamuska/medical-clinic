package com.medical.clinic.controller;

import com.medical.clinic.dto.UserDTO;
import com.medical.clinic.entity.UserEntity;
import com.medical.clinic.filter.UserFilter;
import com.medical.clinic.mapper.PageDTO;
import com.medical.clinic.service.UserService;
import com.medical.clinic.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.medical.clinic.mapper.UserMapper.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<PageDTO<UserDTO>> getUsers(@RequestParam(required = false,defaultValue = "0") Integer page,
                                                     @RequestParam(required = false,defaultValue = "5") Integer size,
                                                     @RequestParam(required = false) String username,
                                                     @RequestParam(required = false) String password,
                                                     @RequestParam(required = false) String email,
                                                     @RequestParam(required = false) String contactNumber,
                                                     @RequestParam(required = false) String sortBy,
                                                     @RequestParam(required = false) String order){

        UserFilter filter = UserFilter.builder()
                .username(username)
                .password(password)
                .email(email)
                .contactNumber(contactNumber)
                .sortBy(sortBy)
                .order(order)
                .build();

        var pageable = PageRequest.of(page,size);
        Page<UserEntity> userPage = userService.getAllUsers(filter,pageable);
        return ResponseEntity.ok(PageUtils.toPageImpl(userPage,USER_MAPPER));

    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.addUser(userDTO));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer userId){
        var user = userService.findById(userId);
        return user!=null?ResponseEntity.ok(user):ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateAppointment(@PathVariable Integer userId,@RequestBody UserDTO userDTO){
        userDTO.setUserId(userId);
        return ResponseEntity.ok(userService.updateUser(userDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer userId){
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }
}
