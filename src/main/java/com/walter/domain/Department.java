package com.walter.domain;

import lombok.*;

import java.util.List;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Department extends BaseDomain {
    private String code;
    private String name;
    private List<Employee> employeeList;
}
