package com.walter.mapper;

import com.walter.domain.Employee;

public interface EmployeeMapperByXml {

    Employee getEmployeeById(Long id);

    Employee getEmployeeByUsername(String username);
}
