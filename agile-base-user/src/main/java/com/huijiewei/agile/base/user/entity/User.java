package com.huijiewei.agile.base.user.entity;

import com.huijiewei.agile.base.entity.DeletedEntity;
import com.huijiewei.agile.base.entity.TimestampEntity;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ag_user")
public class User implements DeletedEntity, TimestampEntity {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String avatar;

    private String createdIp;

    @Enumerated(value = EnumType.STRING)
    private CreatedFromEnums createdFrom;

    @PrePersist
    public void passwordEncode() {
        this.password = passwordEncoder.encode(this.password);
    }

    public static enum CreatedFromEnums {
        WEB,
        APP,
        WECHAT,
        SYSTEM;
    }
}
