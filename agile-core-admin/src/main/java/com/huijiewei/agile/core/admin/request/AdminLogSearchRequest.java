package com.huijiewei.agile.core.admin.request;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.huijiewei.agile.core.admin.entity.AdminLog;
import com.huijiewei.agile.core.request.*;
import com.huijiewei.agile.core.until.DateTimeUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminLogSearchRequest extends BaseSearchRequest {
    private String admin;
    private String[] type;
    private Integer status;
    private String[] createdRange;

    public AdminLogSearchRequest() {
        this
                .addSearchField(new KeywordSearchField().field("admin").label("管理员"))
                .addSearchField(new SelectSearchField()
                        .field("type")
                        .label("日志类型")
                        .multiple(false)
                        .options(AdminLog.typeList())
                )
                .addSearchField(new SelectSearchField()
                        .field("status")
                        .label("操作状态")
                        .options(AdminLog.statusList())
                )
                .addSearchField(new DateTimeRangeSearchField()
                        .field("createdRange")
                        .rangeType("daterange")
                        .labelStart("开始日期")
                        .labelEnd("结束日期")
                        .addShortcut(
                                "最近一周",
                                LocalDate.now().minusWeeks(1).toString(),
                                LocalDate.now().toString()
                        )
                        .addShortcut(
                                "最近一个月",
                                LocalDate.now().minusMonths(1).toString(),
                                LocalDate.now().toString()
                        )
                        .addShortcut(
                                "最近三个月",
                                LocalDate.now().minusMonths(3).toString(),
                                LocalDate.now().toString()
                        )
                        .addShortcut(
                                "最近一年",
                                LocalDate.now().minusYears(1).toString(),
                                LocalDate.now().toString()
                        )
                )
                .addSearchField(new BrSearchField());
    }

    public Specification<AdminLog> getSpecification() {
        // READ https://github.com/wenhao/jpa-spec/blob/master/README_CN.md
        PredicateBuilder<AdminLog> predicateBuilder = Specifications.<AdminLog>and()
                .predicate(
                        StringUtils.isNotEmpty(this.admin),
                        Specifications.or()
                                .like("admin.name", '%' + this.admin + '%')
                                .like("admin.phone", '%' + this.admin + '%')
                                .like("admin.email", '%' + this.admin + '%')
                                .build()
                );

        if (this.type != null && this.type.length > 0) {
            PredicateBuilder<AdminLog> createdFromPredicateBuilder = Specifications.or();

            for (String type : this.type) {
                createdFromPredicateBuilder.eq(StringUtils.isNotEmpty(type), "type", type);
            }

            predicateBuilder.predicate(createdFromPredicateBuilder.build());
        }
        if (this.status != null) {
            predicateBuilder.eq("status", this.status);
        }

        LocalDateTime[] createdRanges = DateTimeUtils.parseSearchDateRange(this.createdRange);

        if (createdRanges != null) {
            predicateBuilder.between("createdAt", createdRanges[0], createdRanges[1]);
        }

        return predicateBuilder.build();
    }
}
