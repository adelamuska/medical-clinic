package com.medical.clinic.specification;

import com.medical.clinic.entity.PrescriptionEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.PrescriptionFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

public class PrescriptionSpecification {

    public static Specification<PrescriptionEntity> filters(PrescriptionFilter filter){
        return (root, query, cb)->{
            var predicates=cb.conjunction();

            if(filter.getDateIssued() != null){
                predicates=cb.and(predicates,cb.equal(root.get("dateIssued"),filter.getDateIssued()));
            }
            if(filter.getMedication() !=null && !filter.getMedication().isEmpty()){
                predicates=cb.and(predicates, cb.like(root.get("medication"), "%" +filter.getMedication()+ "%"));
            }
            if(filter.getDosage() !=null && !filter.getDosage().isEmpty()){
                predicates =cb.and(predicates,cb.like(root.get("dosage"),"%" +filter.getDosage()+ "%"));
            }
            if(filter.getInstruction() !=null && !filter.getInstruction().isEmpty()){
                predicates =cb.and(predicates,cb.like(root.get("instruction"),"%" +filter.getInstruction()+ "%"));
            }




            if (filter.getSortBy() != null && !filter.getSortBy().isEmpty()) {
                List<String> validSortAttributes = Arrays.asList("dateIssued", "medication", "dosage", "instruction");
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
                    throw new ClassicModelException("Sort by must be one of the following: dateIssued, medication, dosage, gender, instruction");
                }
            }
            return predicates;
        };
    }
}
