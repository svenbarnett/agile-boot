package com.huijiewei.agile.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class TimestampEntity extends BaseEntity {
    static final String NOT_DELETED = "deletedAt IS NULL";

    @Column(updatable = false)
    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    LocalDateTime deletedAt;

    public Boolean isDeleted() {
        return this.deletedAt != null;
    }
}
