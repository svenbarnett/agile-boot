package com.huijiewei.agile.base.admin.entity;

import com.huijiewei.agile.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = AdminGroup.TABLE_NAME)
@DynamicInsert
@DynamicUpdate
public class AdminGroup extends BaseEntity {
    public static final String TABLE_NAME = "ag_admin_group";

    private String name;
}
