package com.walter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseDomain {
    private String code;
    private String name;
    private List<Employee> employeeList;
}
