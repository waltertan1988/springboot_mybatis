package com.walter.mapper;

import com.walter.domain.Employee;

public interface EmployeeMapperByXml {

    Employee getEmployeeById(Long id);

    Employee getEmployeeByUsername(String username);

    Long addOne(Employee employee);

    Boolean updateOneByUsername(Employee employee);

    Integer deleteOneByUsername(String username);
}
