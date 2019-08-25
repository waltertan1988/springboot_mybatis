package com.walter.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Employee extends BaseDomain{
    private String username;
    private String userRealName;
    private char gender;
    private String email;
}