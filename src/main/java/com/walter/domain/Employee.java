package com.walter.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
//@Alias("emp")//@Alias会覆盖mybatis.type-aliases-package的设置默认值
public class Employee extends BaseDomain{
    private String username;
    private String userRealName;
    private char gender;
    private String email;
}