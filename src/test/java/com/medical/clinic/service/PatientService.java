package com.medical.clinic.service;

import com.medical.clinic.dto.*;
import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.PatientFilter;
import com.medical.clinic.mapper.PatientMapper;
import com.medical.clinic.repository.PatientRepository;
import com.medical.clinic.specification.PatientSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.medical.clinic.mapper.PatientMapper.PATIENT_MAPPER;
@Service
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

        List<AppointmentDTO> appointmentDTOS = patientDTO.getAppointmentsDTO();
        if(!appointmentDTOS.isEmpty()){
                patientDTO.setAppointmentsDTO(appointmentDTOS);
        }else {
            throw new ClassicModelException("Appointment not found");
        }

        List<PrescriptionDTO> prescriptionDTOS = patientDTO.getPrescriptionsDTO();
        if(!prescriptionDTOS.isEmpty()){
            patientDTO.setPrescriptionsDTO(prescriptionDTOS);
        }else {
            throw new ClassicModelException("Prescription not found");
        }

        List<DoctorDTO> doctorPatientDTOS = patientDTO.getDoctorPatientDTO();
        if(!doctorPatientDTOS.isEmpty()){
            patientDTO.setDoctorPatientDTO(doctorPatientDTOS);
        }else {
            throw new ClassicModelException("Doctor not found");
        }

        List<DiagnoseDTO> diagnosePatientDTOS = patientDTO.getDiagnosePatientDTO();
        if(!diagnosePatientDTOS.isEmpty()){
            patientDTO.setDiagnosePatientDTO(diagnosePatientDTOS);
        }else {
            throw new ClassicModelException("Diagnose not found");
        }

        return PATIENT_MAPPER.toDto(patientRepository.save(entity));
    }

    public PatientDTO findById(Integer patientId) {
        return patientRepository.findById(patientId)
                .map(PATIENT_MAPPER::toDto).orElse(null);
    }

    public PatientDTO updatePatient(PatientDTO patientDTO) {
        var entity = patientRepository.findById(patientDTO.getPatientId())
                .orElseThrow(()-> new ClassicModelException("Patient with id "+patientDTO.getPatientId()+" you are trying to update does not exist"));


//         entity = patientMapper.toEntity(patientDTO);
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
    @Transactional
//    public void deleteById(Integer patientId){
//        var patientFound = patientRepository.findById(patientId)
//                .orElseThrow(() -> new ClassicModelException("Patient with id " + patientId + " does not exist"));
//        patientRepository.delete(patientFound);
//    }

    public void deleteById(Integer patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new ClassicModelException("Patient with id " + patientId + " does not exist"));
        patientRepository.setDeleteTrue(patientId);
    }
}
