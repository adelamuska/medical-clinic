package com.medical.clinic.specification;

import com.medical.clinic.entity.AppointmentEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.AppointmentFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

public class AppointmentSpecification {

    public static Specification<AppointmentEntity> filters(AppointmentFilter filter) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (filter.getAppointmentDate() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("appointmentDate"), filter.getAppointmentDate()));
            }
            if (filter.getStartTime() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("startTime"), filter.getStartTime()));
            }
            if (filter.getEndTime() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("endTime"), filter.getEndTime()));
            }
            if (filter.getDescription() != null && !filter.getDescription().isEmpty()) {
                predicates = cb.and(predicates, cb.like(root.get("description"), "%" + filter.getDescription() + "%"));
            }
            if (filter.getStatus() != null && !filter.getStatus().isEmpty()) {
                predicates = cb.and(predicates, cb.equal(root.get("status"), filter.getStatus()));
            }



            if (filter.getSortBy() != null && !filter.getSortBy().isEmpty()) {
                List<String> validSortAttributes = Arrays.asList("appointmentDate", "startTime", "endTime", "description", "status");
            if (validSortAttributes.contains(filter.getSortBy())){
                if(filter.getOrder()!= null && !filter.getOrder().isEmpty()){
                    if(filter.getOrder().equalsIgnoreCase("ASC")){
                        query.orderBy(cb.asc(root.get(filter.getSortBy())));
                    }else if(filter.getOrder().equalsIgnoreCase("DESC")){
                        query.orderBy(cb.desc(root.get(filter.getSortBy())));
                    } else {
                        throw new ClassicModelException("Order must be ASC or DESC");
                    }
                }
            }else {

                throw new ClassicModelException("Sort by must be one of the following: appointmentDate, startTime, endTime, description,status");
                }
            }
                return predicates;
        };
    }
}
