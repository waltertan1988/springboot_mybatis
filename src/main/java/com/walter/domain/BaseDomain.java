package com.walter.domain;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 使用二级缓存，要对POJO实现Serializable接口
 */
public class BaseDomain implements Serializable {
    protected Long id;
}
