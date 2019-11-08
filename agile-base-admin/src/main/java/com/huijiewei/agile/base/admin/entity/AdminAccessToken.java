package com.huijiewei.agile.base.admin.entity;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "ag_admin_access_token")
public class AdminAccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 0;

    private Integer adminId;
    private String clientId;
    private String accessToken;
    private String userAgent;

    @UpdateTimestamp
    private Instant updatedAt;

    public Boolean isAdult() {
        return this.getId() > 0;
    }
}
