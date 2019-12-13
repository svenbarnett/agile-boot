package com.huijiewei.agile.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Captcha extends BaseEntity {
    private String uuid;

    private String code;

    private LocalDateTime createdAt;
}
