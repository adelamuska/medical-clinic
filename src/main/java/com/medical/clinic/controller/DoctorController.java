package com.medical.clinic.controller;

import com.medical.clinic.dto.DoctorDTO;
import com.medical.clinic.entity.DoctorEntity;
import com.medical.clinic.filter.DoctorFilter;
import com.medical.clinic.mapper.PageDTO;
import com.medical.clinic.service.DoctorService;
import com.medical.clinic.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.medical.clinic.mapper.DoctorMapper.*;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @GetMapping
    public ResponseEntity<PageDTO<DoctorDTO>> getDoctors(@RequestParam(required = false,defaultValue = "0") Integer page,
                                                         @RequestParam(required = false,defaultValue = "5") Integer size,
                                                         @RequestParam(required = false) String firstName,
                                                         @RequestParam(required = false) String lastName,
                                                         @RequestParam(required = false) String specialization,
                                                         @RequestParam(required = false) String sortBy,
                                                         @RequestParam(required = false) String order){

        DoctorFilter filter =DoctorFilter.builder()
                .firstName(firstName)
                .lastName(lastName)
                .specialization(specialization)
                .sortBy(sortBy)
                .order(order)
                .build();

        var pageable = PageRequest.of(page,size);
        Page<DoctorEntity> doctorsPage = doctorService.getAllDoctors(filter,pageable);
        return ResponseEntity.ok(PageUtils.toPageImpl(doctorsPage,DOCTOR_MAPPER));

    }

    @PostMapping
    public ResponseEntity<DoctorDTO> addDoctor(@RequestBody DoctorDTO doctorDTO){
        return ResponseEntity.ok(doctorService.addDoctor(doctorDTO));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorDTO> findById(@PathVariable Integer doctorId){
        var doctor = doctorService.findById(doctorId);
        return doctor!=null?ResponseEntity.ok(doctor):ResponseEntity.notFound().build();
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Integer doctorId,@RequestBody DoctorDTO doctorDTO){
        doctorDTO.setDoctorId(doctorId);
        return ResponseEntity.ok(doctorService.updateDoctor(doctorDTO));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer doctorId){
        doctorService.deleteById(doctorId);
        return ResponseEntity.ok().build();
    }
}
