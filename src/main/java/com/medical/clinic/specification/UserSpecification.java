package com.medical.clinic.specification;

import com.medical.clinic.entity.UserEntity;
import com.medical.clinic.exception.ClassicModelException;
import com.medical.clinic.filter.UserFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

public class UserSpecification {

    public static Specification<UserEntity> filters(UserFilter filter){
        return (root, query, cb)->{
            var predicates=cb.conjunction();

            if(filter.getUsername() != null  && !filter.getUsername().isEmpty()){
                predicates=cb.and(predicates,cb.like(root.get("username"),"%" +filter.getUsername()+ "%"));
            }
            if(filter.getPassword() !=null && !filter.getPassword().isEmpty()){
                predicates=cb.and(predicates, cb.like(root.get("password"), "%" +filter.getPassword()+ "%"));
            }
            if(filter.getEmail() !=null && !filter.getEmail().isEmpty()){
                predicates =cb.and(predicates,cb.like(root.get("email"),"%" +filter.getEmail()+ "%"));
            }
            if(filter.getRole() !=null && !filter.getRole().isEmpty()){
                predicates =cb.and(predicates,cb.like(root.get("role"),"%" +filter.getEmail()+ "%"));
            }
            if(filter.getContactNumber() !=null && !filter.getContactNumber().isEmpty()){
                predicates =cb.and(predicates,cb.like(root.get("contactNumber"),"%" +filter.getContactNumber()+ "%"));
            }



            if (filter.getSortBy() != null && !filter.getSortBy().isEmpty()) {
                List<String> validSortAttributes = Arrays.asList("username", "password", "email", "contactNumber", "role");
                if (validSortAttributes.contains(filter.getSortBy())) {
                    if (filter.getOrder() != null && !filter.getOrder().isEmpty()) {
                        if (filter.getOrder().equalsIgnoreCase("ASC")) {
                            query.orderBy(cb.asc(root.get(filter.getSortBy())));
                        } else if (filter.getOrder().equalsIgnoreCase("DESC")) {
                            query.orderBy(cb.desc(root.get(filter.getSortBy())));
                        } else {
                            throw new ClassicModelException("Order must be ASC or DESC");
                        }
                    }
                } else {
                    throw new ClassicModelException("Sort by must be one of the following: username, password, email, contactNumber");
                }
            }
           return predicates;
        };
    }
}
