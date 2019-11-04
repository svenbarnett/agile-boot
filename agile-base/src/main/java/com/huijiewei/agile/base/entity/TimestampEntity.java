package com.huijiewei.agile.base.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public interface TimestampEntity {
    @CreationTimestamp
    Instant createdAt = null;

    @UpdateTimestamp
    Instant updatedAt = null;
}
