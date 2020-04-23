package com.huijiewei.agile.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class Identity extends BaseEntity {
    private String phone;

    private String email;

    private String password;

    public abstract IdentityLog createLog();
}
