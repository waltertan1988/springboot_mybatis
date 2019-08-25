package com.walter.mapper;

import com.walter.domain.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapperByAnnotation {

    @Select("select * from EMPLOYEE where id = #{id}")
    Employee getEmployeeById(Long id);
}
