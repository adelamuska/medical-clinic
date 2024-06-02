package com.medical.clinic.specification;

import com.medical.clinic.entity.RoleEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.RoleFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

public class RoleSpecification {

    public static Specification<RoleEntity> filters(RoleFilter filter){
        return (root, query, cb)->{
            var predicates=cb.conjunction();

            if(filter.getRoleName() != null && !filter.getRoleName().isEmpty()){
                predicates=cb.and(predicates,cb.like(root.get("roleName"),"%" +filter.getRoleName()+ "%"));
            }



            if (filter.getSortBy() != null && !filter.getSortBy().isEmpty()) {
                List<String> validSortAttributes = Arrays.asList("roleName");
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
                    throw new ClassicModelException("Sort by must be the following: roleName");
                }
            }
            return predicates;
        };
    }
}
