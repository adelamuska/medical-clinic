package com.medical.clinic.controller;

import com.medical.clinic.dto.PatientDTO;
import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.filter.PatientFilter;
import com.medical.clinic.mapper.PageDTO;
import com.medical.clinic.service.PatientService;
import com.medical.clinic.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.medical.clinic.mapper.PatientMapper.*;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping
    public ResponseEntity<PageDTO<PatientDTO>> getPatients(@RequestParam(required = false,defaultValue = "0") Integer page,
                                                           @RequestParam(required = false,defaultValue = "5") Integer size,
                                                           @RequestParam(required = false) String firstName,
                                                           @RequestParam(required = false) String lastName,
                                                           @RequestParam(required = false) LocalDate birthDate,
                                                           @RequestParam(required = false) String gender,
                                                           @RequestParam(required = false) String address,
                                                           @RequestParam(required = false) String sortBy,
                                                           @RequestParam(required = false) String order){

        PatientFilter filter = PatientFilter.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .gender(gender)
                .address(address)
                .sortBy(sortBy)
                .order(order)
                .build();

        var pageable = PageRequest.of(page,size);
        Page<PatientEntity> patientsPage = patientService.getAllPatients(filter,pageable);
        return ResponseEntity.ok(PageUtils.toPageImpl(patientsPage,PATIENT_MAPPER));

    }

    @PostMapping
    public ResponseEntity<PatientDTO> addPatient(@RequestBody PatientDTO patientDTO){
        return ResponseEntity.ok(patientService.addPatient(patientDTO));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDTO> findById(@PathVariable Integer patientId){
        var patient = patientService.findById(patientId);
        return patient!=null?ResponseEntity.ok(patient):ResponseEntity.notFound().build();
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Integer patientId,@RequestBody PatientDTO patientDTO){
        patientDTO.setPatientId(patientId);
        return ResponseEntity.ok(patientService.updatePatient(patientDTO));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer patientId){
        patientService.deleteById(patientId);
        return ResponseEntity.ok().build();
    }
}
