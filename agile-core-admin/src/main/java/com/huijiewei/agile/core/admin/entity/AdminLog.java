package com.huijiewei.agile.core.admin.entity;

import com.huijiewei.agile.core.entity.IdentityLog;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class AdminLog extends IdentityLog<AdminLog> {
    private Integer adminId;

    @ManyToOne
    @JoinColumn(name = "adminId", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Admin admin;
}
