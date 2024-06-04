package com.medical.clinic.service;

import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.AppointmentFilter;
import com.medical.clinic.specification.AppointmentSpecification;
import com.medical.clinic.repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.medical.clinic.mapper.AppointmentMapper.APPOINTMENT_MAPPER;

@Service
@Transactional
public class AppointmentService {


    @Autowired
    AppointmentRepository appointmentRepository;
    public Page<AppointmentEntity> getAllAppointments(AppointmentFilter filter,Pageable pageable){

        return  appointmentRepository.findAll(AppointmentSpecification.filters(filter),pageable);
    }


    public AppointmentDTO addAppointment(AppointmentDTO appointmentDTO) {
        var entity = APPOINTMENT_MAPPER.toEntity(appointmentDTO);
        return APPOINTMENT_MAPPER.toDto(appointmentRepository.save(entity));
    }

    public AppointmentDTO findById(Integer appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(APPOINTMENT_MAPPER::toDto).orElse(null);
    }

    public AppointmentDTO updateAppointment(AppointmentDTO appointmentDTO) {
        var entity = appointmentRepository.findById(appointmentDTO.getAppointmentId())
                .orElseThrow(()-> new ClassicModelException("Appointment with id "+appointmentDTO.getAppointmentId()+" you are trying to update does not exist"));
        entity = APPOINTMENT_MAPPER.toEntity(appointmentDTO);
        return APPOINTMENT_MAPPER.toDto(appointmentRepository.save(entity));
    }

    public void deleteById(Integer appointmentId){
        appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ClassicModelException("Appointment with id " + appointmentId + " does not exist"));
        appointmentRepository.setDeleteTrue(appointmentId);

    }
}
