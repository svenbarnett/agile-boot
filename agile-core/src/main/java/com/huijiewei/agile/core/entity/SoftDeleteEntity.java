package com.huijiewei.agile.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@Where(clause = SoftDeleteEntity.NOT_DELETED)
public abstract class SoftDeleteEntity extends BaseEntity {
    static final String NOT_DELETED = "deletedAt IS NULL";

    LocalDateTime deletedAt;

    public Boolean isDeleted() {
        return this.deletedAt != null;
    }
}
