package com.huijiewei.agile.base.admin.entity;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "ag_admin_group")
public class AdminGroup {
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.Detail.class, Admin.Views.All.class})
    @Schema(description = "管理组 Id")
    private Integer id;

    @NotBlank
    @JsonView({Views.All.class, Admin.Views.Detail.class})
    @Schema(description = "管理组名称")
    private String name;
}
