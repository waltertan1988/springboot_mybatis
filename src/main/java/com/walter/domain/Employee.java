package com.walter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
//@Alias("emp")//@Alias会覆盖mybatis.type-aliases-package的设置默认值
public class Employee extends BaseDomain{
    private String username;
    private String userRealName;
    private char gender;
    private String email;
}