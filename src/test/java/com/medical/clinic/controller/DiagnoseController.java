package com.medical.clinic.controller;

import com.medical.clinic.dto.DiagnoseDTO;
import com.medical.clinic.entity.DiagnoseEntity;
import com.medical.clinic.filter.DiagnoseFilter;
import com.medical.clinic.mapper.PageDTO;
import com.medical.clinic.service.DiagnoseService;
import com.medical.clinic.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.medical.clinic.mapper.DiagnoseMapper.DIAGNOSE_MAPPER;

@RestController
@RequestMapping("/api/v1/diagnoses")
public class DiagnoseController {

    @Autowired
    DiagnoseService diagnoseService;

    @GetMapping
    public ResponseEntity<PageDTO<DiagnoseDTO>> getDiagnoses(@RequestParam(required = false,defaultValue = "0") Integer page,
                                                             @RequestParam(required = false,defaultValue = "5") Integer size,
                                                             @RequestParam(required = false) LocalDate dateDiagnosed,
                                                             @RequestParam(required = false) String diagnosis,
                                                             @RequestParam(required = false) String details,
                                                             @RequestParam(required = false) String sortBy,
                                                             @RequestParam(required = false) String order){

        DiagnoseFilter filter = DiagnoseFilter.builder()
                .dateDiagnosed(dateDiagnosed)
                .diagnosis(diagnosis)
                .details(details)
                .sortBy(sortBy)
                .order(order)
                .build();

        var pageable = PageRequest.of(page,size);
        Page<DiagnoseEntity> diagnosesPage = diagnoseService.getAllDiagnoses(filter,pageable);
        return ResponseEntity.ok(PageUtils.toPageImpl(diagnosesPage,DIAGNOSE_MAPPER));

    }

    @PostMapping
    public ResponseEntity<DiagnoseDTO> addDiagnose(@RequestBody DiagnoseDTO diagnoseDTO){
        return ResponseEntity.ok(diagnoseService.addDiagnose(diagnoseDTO));
    }

    @GetMapping("/{diagnoseId}")
    public ResponseEntity<DiagnoseDTO> findById(@PathVariable Integer diagnoseId){
        var diagnose = diagnoseService.findById(diagnoseId);
        return diagnose!=null?ResponseEntity.ok(diagnose):ResponseEntity.notFound().build();
    }

    @PutMapping("/{diagnoseId}")
    public ResponseEntity<DiagnoseDTO> updateDiagnose(@PathVariable Integer diagnoseId,@RequestBody DiagnoseDTO diagnoseDTO){
        diagnoseDTO.setDiagnosisId(diagnoseId);
        return ResponseEntity.ok(diagnoseService.updateDiagnose(diagnoseDTO));
    }

    @DeleteMapping("/{diagnoseId}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer diagnoseId){
        diagnoseService.deleteById(diagnoseId);
        return ResponseEntity.ok().build();
    }
}
