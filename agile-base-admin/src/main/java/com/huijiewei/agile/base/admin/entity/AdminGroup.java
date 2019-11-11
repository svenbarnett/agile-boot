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
@Table(name = "ag_admin_group")
@DynamicInsert
@DynamicUpdate
public class AdminGroup extends BaseEntity {
    private String name;
}
