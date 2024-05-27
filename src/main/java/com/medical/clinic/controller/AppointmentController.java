package com.medical.clinic.controller;


import com.medical.clinic.dto.AppointmentDTO;
import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.filter.AppointmentFilter;
import com.medical.clinic.mapper.PageDTO;
import com.medical.clinic.service.AppointmentService;
import com.medical.clinic.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalTime;

import static com.medical.clinic.mapper.AppointmentMapper.*;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<PageDTO<AppointmentDTO>> getAppointments(@RequestParam(required = false,defaultValue = "0") Integer page,
                                                                   @RequestParam(required = false,defaultValue = "5") Integer size,
                                                                   @RequestParam(required = false) LocalDate appointmentDate,
                                                                   @RequestParam(required = false) LocalTime startTime,
                                                                   @RequestParam(required = false) LocalTime endTime,
                                                                   @RequestParam(required = false) String description,
                                                                   @RequestParam(required = false) String status,
                                                                   @RequestParam(required = false) String sortBy,
                                                                   @RequestParam(required = false) String order) {

            AppointmentFilter filter = AppointmentFilter.builder()
                    .appointmentDate(appointmentDate)
                    .startTime(startTime)
                    .endTime(endTime)
                    .description(description)
                    .status(status)
                    .sortBy(sortBy)
                    .order(order)
                    .build();
        var pageable = PageRequest.of(page,size);
        Page<AppointmentEntity> appointmentPage = appointmentService.getAllAppointments(filter,pageable);
        return ResponseEntity.ok(PageUtils.toPageImpl(appointmentPage,APPOINTMENT_MAPPER));

    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> addAppointment(@RequestBody AppointmentDTO appointmentDTO){
        return ResponseEntity.ok(appointmentService.addAppointment(appointmentDTO));
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Integer appointmentId){
        var appointment = appointmentService.findById(appointmentId);
        return appointment!=null?ResponseEntity.ok(appointment):ResponseEntity.notFound().build();
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Integer appointmentId,@RequestBody AppointmentDTO appointmentDTO){
        appointmentDTO.setAppointmentId(appointmentId);
        return ResponseEntity.ok(appointmentService.updateAppointment(appointmentDTO));
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer appointmentId){
        appointmentService.deleteById(appointmentId);
        return ResponseEntity.ok().build();
    }
}
