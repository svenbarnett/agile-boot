package com.huijiewei.agile.base.admin.entity;

import com.huijiewei.agile.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ag_admin_access_token")
public class AdminAccessToken extends BaseEntity {
    private Integer adminId;
    private String clientId;
    private String accessToken;
    private String userAgent;

    @UpdateTimestamp
    Instant updatedAt = null;
}