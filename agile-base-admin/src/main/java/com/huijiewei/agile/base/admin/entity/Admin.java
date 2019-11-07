package com.huijiewei.agile.base.admin.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.huijiewei.agile.base.constraint.Phone;
import com.huijiewei.agile.base.entity.DeletedEntity;
import com.huijiewei.agile.base.entity.TimestampEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "ag_admin")
public class Admin implements DeletedEntity, TimestampEntity {
    public interface Views {
        public static class All {
        }

        public static class Detail extends All {
        }

        public static class Create extends All {
        }

        public static class Edit extends All {
        }
    }

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.Detail.class})
    @Schema(description = "管理员 Id")
    private Integer id;

    @NotBlank
    @Phone
    @Column(unique = true)
    @JsonView({Views.All.class})
    @Schema(description = "管理员电话")
    private String phone;

    @NotBlank
    @Email
    @Column(unique = true)
    @JsonView({Views.All.class})
    @Schema(description = "管理员邮箱")
    private String email;

    @NotBlank
    @JsonView({Views.Create.class, Views.Edit.class})
    @Schema(description = "管理员密码")
    private String password;

    @NotBlank
    @JsonView({Views.All.class})
    @Schema(description = "管理员名称")
    private String name;

    @NotNull
    @JsonView({Views.All.class})
    @Schema(description = "管理员头像")
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "adminGroupId")
    @JsonView({Views.Detail.class})
    @Schema(description = "所属管理组")
    private AdminGroup adminGroup;

    @PrePersist
    public void passwordEncode() {
        this.password = passwordEncoder.encode(this.password);
    }
}
