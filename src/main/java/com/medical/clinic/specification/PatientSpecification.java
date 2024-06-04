package com.medical.clinic.specification;

import com.medical.clinic.entity.PatientEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.PatientFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

public class PatientSpecification {

    public static Specification<PatientEntity> filters(PatientFilter filter){
        return (root,query,cb)-> {
            var predicates = cb.conjunction();

            if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
                predicates = cb.and(predicates, cb.like(root.get("firstName"), "%" + filter.getFirstName() + "%"));
            }
            if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
                predicates = cb.and(predicates, cb.like(root.get("lastName"), "%" + filter.getLastName() + "%"));
            }
            if (filter.getBirthDate() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("birthDate"), filter.getBirthDate()));
            }
            if (filter.getGender() != null && !filter.getGender().isEmpty()) {
                predicates = cb.and(predicates, cb.like(root.get("gender"), "%" + filter.getGender() + "%"));
            }
            if (filter.getAddress() != null && !filter.getAddress().isEmpty()) {
                predicates = cb.and(predicates, cb.like(root.get("address"), "%" + filter.getAddress() + "%"));
            }


            if (filter.getSortBy() != null && !filter.getSortBy().isEmpty()) {
                List<String> validSortAttributes = Arrays.asList("firstName", "lastName", "birthDate", "gender", "address");
                if (validSortAttributes.contains(filter.getSortBy())) {
                    if (filter.getOrder() != null && !filter.getOrder().isEmpty()) {
                        if (filter.getOrder().equalsIgnoreCase("ASC")) {
                            query.orderBy(cb.asc(root.get(filter.getSortBy())));
                        } else if ((filter.getOrder().equalsIgnoreCase("DESC"))) {
                            query.orderBy(cb.desc(root.get(filter.getSortBy())));
                        } else {
                            throw new ClassicModelException("Order must be ASC or DESC");
                        }
                    }
                } else {
                    throw new ClassicModelException("Sort by must be one of the following: firstName, lastName, birthDate, gender, address");
                }
            }

                return predicates;
            }
            ;
        }
}
