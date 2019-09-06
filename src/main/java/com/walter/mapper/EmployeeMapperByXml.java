package com.walter.mapper;

import com.walter.domain.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapperByXml {

    Employee getEmployeeById(Long id);

    Employee getEmployeeByUsername(String username);

    Long addOne(Employee employee);

    Boolean updateOneByUsername(Employee employee);

    Integer deleteOneByUsername(String username);

    Boolean isIdMatchUsername(@Param("id") Long id, @Param("username") String username);

    List<Employee> findByUserRealName(String userRealName);

    Map<String, Object> getEmployeeMapByUsername(String username);
}
