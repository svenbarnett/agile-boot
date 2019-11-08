package com.huijiewei.agile.base.user.entity;

import com.huijiewei.agile.base.entity.TimestampEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ag_user")
public class User extends TimestampEntity {
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

    public enum CreatedFromEnums {
        WEB("网站"),
        APP("APP"),
        WECHAT("微信"),
        SYSTEM("系统");

        private String description;

        private CreatedFromEnums(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }
}
