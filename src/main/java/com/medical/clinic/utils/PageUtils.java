package com.medical.clinic.utils;

import com.medical.clinic.mapper.BaseMapper;
import com.medical.clinic.mapper.PageDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PageUtils {
    public static <E,D> PageDTO<D> toPageImpl(Page<E> page, BaseMapper<E,D> mapper){
        List<D> entities = page.getContent().stream().map(o -> mapper.toDto(o)).collect(Collectors.toList());
        PageDTO<D> output = new PageDTO<>(entities,page.getTotalElements(),page.getTotalPages(),page.getSize(),page.isFirst(),page.isLast());
        return output;
    }
}