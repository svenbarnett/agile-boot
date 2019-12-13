package com.huijiewei.agile.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Captcha extends BaseEntity {
    private String uuid;

    private String code;

    private String userAgent;

    private String remoteAddr;

    private LocalDateTime createdAt;
}
