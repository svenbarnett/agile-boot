package com.huijiewei.agile.core.entity;

import com.huijiewei.agile.core.config.PrefixTableNamingStrategy;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public static String tableName(Class<? extends BaseEntity> entityClass) {
        return PrefixTableNamingStrategy.toPhysicalTableName(entityClass.getSimpleName());
    }

    public Boolean hasId() {
        return this.getId() != null && this.getId() > 0;
    }
}
