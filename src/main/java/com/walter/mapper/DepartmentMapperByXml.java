package com.walter.mapper;

import com.walter.domain.Department;

public interface DepartmentMapperByXml {

    Department getDepartmentById(Long id);

    Long deleteByCode(String code);

    Department getDepartmentByCode(String code);

    Department getDepartmentWithEmployeesByCode(String code);

    Department getDepartmentWithEmployeesByCodeUsingResultMapBy2Steps(String code);
}
