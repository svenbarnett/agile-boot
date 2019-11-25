package com.huijiewei.agile.base.admin.entity;

import com.huijiewei.agile.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = AdminGroupPermission.TABLE_NAME)
public class AdminGroupPermission extends BaseEntity {
    public static final String TABLE_NAME = "ag_admin_group_permission";

    private Integer adminGroupId;

    private String actionId;
}
