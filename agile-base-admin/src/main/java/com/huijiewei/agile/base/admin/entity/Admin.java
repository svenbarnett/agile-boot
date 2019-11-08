package com.huijiewei.agile.base.admin.entity;

import com.huijiewei.agile.base.entity.DeletedEntity;
import com.huijiewei.agile.base.entity.TimestampEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ag_admin")
public class Admin implements DeletedEntity, TimestampEntity {
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

    @ManyToOne
    @JoinColumn(name = "adminGroupId")
    private AdminGroup adminGroup;

    @PrePersist
    public void passwordEncode() {
        this.password = passwordEncoder.encode(this.password);
    }
}
