package com.huijiewei.agile.base.admin.entity;

import com.huijiewei.agile.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ag_admin")
public class Admin extends BaseEntity {
    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
