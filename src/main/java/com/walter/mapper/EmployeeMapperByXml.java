package com.walter.mapper;

import com.walter.domain.Employee;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapperByXml {

    Employee getEmployeeById(Long id);

    Employee getEmployeeByUsername(String username);

    Long addOne(Employee employee);

    Boolean updateOneByUsername(Employee employee);

    Integer deleteOneByUsername(String username);

    Boolean isIdMatchUsername(@Param("id") Long id, @Param("username") String username);
}
