package com.medical.clinic.service;

import com.medical.clinic.dto.*;
import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.PatientFilter;
import com.medical.clinic.mapper.PatientMapper;
import com.medical.clinic.specification.PatientSpecification;
import com.medical.clinic.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import static com.medical.clinic.mapper.PatientMapper.PATIENT_MAPPER;
@Service
@Transactional
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientMapper patientMapper;


    public Page<PatientEntity> getAllPatients(PatientFilter filter,Pageable pageable){
        return patientRepository.findAll(PatientSpecification.filters(filter),pageable);
    }

    public PatientDTO addPatient(PatientDTO patientDTO) {
        var entity = PATIENT_MAPPER.toEntity(patientDTO);
        return PATIENT_MAPPER.toDto(patientRepository.save(entity));
    }

    public PatientDTO findById(Integer patientId) {
        return patientRepository.findById(patientId)
                .map(PATIENT_MAPPER::toDto).orElse(null);
    }

    public PatientDTO updatePatient(PatientDTO patientDTO) {
        var entity = patientRepository.findById(patientDTO.getPatientId())
                .orElseThrow(()-> new ClassicModelException("Patient with id "+patientDTO.getPatientId()+" you are trying to update does not exist"));


       var patientToUpdate = patientMapper.toEntity(patientDTO);

        if(patientToUpdate.getPrescriptions().isEmpty()){
            patientToUpdate.setPrescriptions(entity.getPrescriptions());
        }
        if(patientToUpdate.getAppointments().isEmpty()){
            patientToUpdate.setAppointments(entity.getAppointments());
        }
        if(patientToUpdate.getDoctorPatient().isEmpty()){
            patientToUpdate.setDoctorPatient(entity.getDoctorPatient());
        }
        if(patientToUpdate.getDiagnosePatient().isEmpty()){
            patientToUpdate.setDiagnosePatient(entity.getDiagnosePatient());
        }

        return PATIENT_MAPPER.toDto(patientRepository.save(patientToUpdate));
    }

    public void deleteById(Integer patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new ClassicModelException("Patient with id " + patientId + " does not exist"));
        patientRepository.setDeleteTrue(patientId);
    }
}
