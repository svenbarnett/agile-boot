package com.huijiewei.agile.core.admin.entity;

import com.huijiewei.agile.core.constraint.Unique;
import com.huijiewei.agile.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicInsert
@DynamicUpdate

@Unique(fields = {"name"}, message = "管理组已存在")
public class AdminGroup extends BaseEntity {
    private String name;
}
