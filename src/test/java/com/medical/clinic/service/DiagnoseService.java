package com.medical.clinic.service;

import com.medical.clinic.dto.DiagnoseDTO;
import com.medical.clinic.entity.DiagnoseEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.DiagnoseFilter;
import com.medical.clinic.repository.DiagnoseRepository;
import com.medical.clinic.specification.DiagnoseSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.medical.clinic.mapper.DiagnoseMapper.DIAGNOSE_MAPPER;
@Service
@Transactional
public class DiagnoseService {


    @Autowired
    DiagnoseRepository diagnoseRepository;

    public Page<DiagnoseEntity> getAllDiagnoses(DiagnoseFilter filter,Pageable pageable) {
        return diagnoseRepository.findAll(DiagnoseSpecification.filters(filter),pageable);
    }

    public DiagnoseDTO addDiagnose(DiagnoseDTO diagnoseDTO) {
        var entity = DIAGNOSE_MAPPER.toEntity(diagnoseDTO);
        return DIAGNOSE_MAPPER.toDto(diagnoseRepository.save(entity));
    }

    public DiagnoseDTO findById(Integer diagnoseId) {
        return diagnoseRepository.findById(diagnoseId)
                .map(DIAGNOSE_MAPPER::toDto).orElse(null);
    }

    public DiagnoseDTO updateDiagnose(DiagnoseDTO diagnoseDTO) {
        var entity = diagnoseRepository.findById(diagnoseDTO.getDiagnosisId())
                .orElseThrow(() -> new ClassicModelException("Diagnose with id " + diagnoseDTO.getDiagnosisId() + " you are trying to update does not exist"));
        entity = DIAGNOSE_MAPPER.toEntity(diagnoseDTO);
        return DIAGNOSE_MAPPER.toDto(diagnoseRepository.save(entity));
    }


//    public void deleteById(Integer diagnoseId){
//        var diagnoseFound = diagnoseRepository.findById(diagnoseId)
//                .orElseThrow(() -> new ClassicModelException("Diagnose with id " + diagnoseId + " does not exist"));
//        diagnoseRepository.delete(diagnoseFound);
//    }

    public void deleteById(Integer diagnoseId) {
        diagnoseRepository.findById(diagnoseId)
                .orElseThrow(() -> new ClassicModelException("Diagnose with id " + diagnoseId + " does not exist"));
        diagnoseRepository.setDeleteTrue(diagnoseId);
    }
}