package com.huijiewei.agile.core.admin.entity;

import com.huijiewei.agile.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class AdminAccessToken extends BaseEntity {
    private Integer adminId;
    private String clientId;
    private String accessToken;
    private String remoteAddr;
    private String userAgent;

    private Instant updatedAt;
}
