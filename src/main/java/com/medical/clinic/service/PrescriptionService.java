package com.medical.clinic.service;

import com.medical.clinic.dto.PrescriptionDTO;
import com.medical.clinic.entity.PrescriptionEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.PrescriptionFilter;
import com.medical.clinic.specification.PrescriptionSpecification;
import com.medical.clinic.repository.PrescriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.medical.clinic.mapper.PrescriptionMapper.PRESCRIPTION_MAPPER;
@Service
@Transactional
public class PrescriptionService {

    @Autowired
    PrescriptionRepository prescriptionRepository;
    public Page<PrescriptionEntity> getAllPrescriptions(PrescriptionFilter filter,Pageable pageable){
        return  prescriptionRepository.findAll(PrescriptionSpecification.filters(filter),pageable);
    }

    public PrescriptionDTO addPrescription(PrescriptionDTO prescriptionDTO) {
        var entity = PRESCRIPTION_MAPPER.toEntity(prescriptionDTO);
        return PRESCRIPTION_MAPPER.toDto(prescriptionRepository.save(entity));
    }

    public PrescriptionDTO findById(Integer prescriptionId) {
        return prescriptionRepository.findById(prescriptionId)
                .map(PRESCRIPTION_MAPPER::toDto).orElse(null);
    }

    public PrescriptionDTO updatePrescription(PrescriptionDTO prescriptionDTO) {
        var entity = prescriptionRepository.findById(prescriptionDTO.getPrescriptionId())
                .orElseThrow(()-> new ClassicModelException("Prescription with id "+prescriptionDTO.getPrescriptionId()+" you are trying to update does not exist"));
        entity = PRESCRIPTION_MAPPER.toEntity(prescriptionDTO);
        return PRESCRIPTION_MAPPER.toDto(prescriptionRepository.save(entity));
    }

    public void deleteById(Integer prescriptionId) {
        prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ClassicModelException("Prescription with id " + prescriptionId + " does not exist"));
        prescriptionRepository.setDeleteTrue(prescriptionId);
    }
}
