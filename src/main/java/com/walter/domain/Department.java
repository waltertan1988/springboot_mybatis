package com.walter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseDomain {
    private String code;
    private String name;
}
