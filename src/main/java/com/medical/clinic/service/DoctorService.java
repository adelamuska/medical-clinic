package com.medical.clinic.service;

import com.medical.clinic.dto.DoctorDTO;
import com.medical.clinic.entity.DoctorEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.DoctorFilter;
import com.medical.clinic.mapper.DoctorMapper;
import com.medical.clinic.specification.DoctorSpecification;
import com.medical.clinic.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.medical.clinic.mapper.DoctorMapper.DOCTOR_MAPPER;
@Service
@Transactional
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    DoctorMapper doctorMapper;
    public Page<DoctorEntity> getAllDoctors(DoctorFilter filter, Pageable pageable){
        return doctorRepository.findAll(DoctorSpecification.filters(filter),pageable);
    }

    public DoctorDTO addDoctor(DoctorDTO doctorDTO) {
        var entity = DOCTOR_MAPPER.toEntity(doctorDTO);
        return DOCTOR_MAPPER.toDto(doctorRepository.save(entity));
    }

    public DoctorDTO findById(Integer doctorId) {
        return doctorRepository.findById(doctorId)
                .map(DOCTOR_MAPPER::toDto).orElse(null);
    }

    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) {
        var entity = doctorRepository.findById(doctorDTO.getDoctorId())
                .orElseThrow(() -> new ClassicModelException("Doctor with id " + doctorDTO.getDoctorId() + " you are trying to update does not exist"));

        var doctorToUpdate = doctorMapper.toEntity(doctorDTO);

        if (doctorToUpdate.getDiagnoses().isEmpty()) {
            doctorToUpdate.setDiagnoses(entity.getDiagnoses());
        }
        if (doctorToUpdate.getPrescriptions().isEmpty()) {
            doctorToUpdate.setPrescriptions(entity.getPrescriptions());
        }
        if (doctorToUpdate.getAppointments().isEmpty()) {
            doctorToUpdate.setAppointments(entity.getAppointments());
        }
            return DOCTOR_MAPPER.toDto(doctorRepository.save(doctorToUpdate));
        }


    public void deleteById(Integer doctorId) {
        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ClassicModelException("Doctor with id " + doctorId + " does not exist"));
        doctorRepository.setDeleteTrue(doctorId);
    }

}
