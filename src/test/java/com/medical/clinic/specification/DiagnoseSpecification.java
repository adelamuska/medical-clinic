package com.medical.clinic.specification;

import com.medical.clinic.entity.DiagnoseEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.DiagnoseFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

public class DiagnoseSpecification {

    public static Specification<DiagnoseEntity> filters(DiagnoseFilter filter){
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (filter.getDateDiagnosed() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("dateDiagnosed"), filter.getDateDiagnosed()));
            }
            if (filter.getDiagnosis() != null && !filter.getDiagnosis().isEmpty()) {
                predicates = cb.and(predicates, cb.like(root.get("diagnosis"), "%" + filter.getDiagnosis() + "%"));
            }
            if (filter.getDetails() != null && !filter.getDetails().isEmpty()) {
                predicates = cb.and(predicates, cb.like(root.get("details"), "%" + filter.getDetails() + "%"));
            }



            if (filter.getSortBy() != null && !filter.getSortBy().isEmpty()) {
                List<String> validSortAttributes = Arrays.asList("dateDiagnosed", "diagnosis", "details");
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
                    throw new ClassicModelException("Sort by must be one of the following: dateDiagnosed, diagnosis, details");
                }
            }
            return predicates;
        };
    }
}
