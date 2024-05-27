package com.medical.clinic.controller;

import com.medical.clinic.dto.PrescriptionDTO;
import com.medical.clinic.entity.PrescriptionEntity;
import com.medical.clinic.filter.PrescriptionFilter;
import com.medical.clinic.mapper.PageDTO;
import com.medical.clinic.service.PrescriptionService;
import com.medical.clinic.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.medical.clinic.mapper.PrescriptionMapper.*;

@RestController
@RequestMapping("/api/v1/prescriptions")
public class PrescriptionController {

    @Autowired
    PrescriptionService prescriptionService;

    @GetMapping
    public ResponseEntity<PageDTO<PrescriptionDTO>> getPrescriptions(@RequestParam(required = false,defaultValue = "0") Integer page,
                                                                     @RequestParam(required = false,defaultValue = "5") Integer size,
                                                                     @RequestParam(required = false) LocalDate dateIssued,
                                                                     @RequestParam(required = false) String medication,
                                                                     @RequestParam(required = false) String dosage,
                                                                     @RequestParam(required = false) String instruction,
                                                                     @RequestParam(required = false) String sortBy,
                                                                     @RequestParam(required = false) String order){

        PrescriptionFilter filter = PrescriptionFilter.builder()
                .dateIssued(dateIssued)
                .medication(medication)
                .dosage(dosage)
                .instruction(instruction)
                .sortBy(sortBy)
                .order(order)
                .build();

        var pageable = PageRequest.of(page,size);
        Page<PrescriptionEntity> prescriptionPage = prescriptionService.getAllPrescriptions(filter,pageable);
        return ResponseEntity.ok(PageUtils.toPageImpl(prescriptionPage,PRESCRIPTION_MAPPER));

    }

    @PostMapping
    public ResponseEntity<PrescriptionDTO> addPrescription(@RequestBody PrescriptionDTO PrescriptionDTO){
        return ResponseEntity.ok(prescriptionService.addPrescription(PrescriptionDTO));
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<PrescriptionDTO> findById(@PathVariable Integer prescriptionId){
        var prescription = prescriptionService.findById(prescriptionId);
        return prescription!=null?ResponseEntity.ok(prescription):ResponseEntity.notFound().build();
    }

    @PutMapping("/{prescriptionId}")
    public ResponseEntity<PrescriptionDTO> updatePrescription(@PathVariable Integer prescriptionId,@RequestBody PrescriptionDTO prescriptionDTO){
        prescriptionDTO.setPrescriptionId(prescriptionId);
        return ResponseEntity.ok(prescriptionService.updatePrescription(prescriptionDTO));
    }

    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer prescriptionId){
        prescriptionService.deleteById(prescriptionId);
        return ResponseEntity.ok().build();
    }

}
