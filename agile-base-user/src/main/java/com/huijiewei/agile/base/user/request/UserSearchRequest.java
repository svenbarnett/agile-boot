package com.huijiewei.agile.base.user.request;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.huijiewei.agile.base.request.*;
import com.huijiewei.agile.base.until.DateTimeUtils;
import com.huijiewei.agile.base.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserSearchRequest extends BaseSearchRequest {
    private String name;
    private String phone;
    private String email;
    private String[] createdFrom;
    private String[] createdRange;

    public UserSearchRequest() {
        this
                .addSearchField(new KeywordSearchField().field("phone").label("手机号码"))
                .addSearchField(new KeywordSearchField().field("email").label("电子邮箱"))
                .addSearchField(new KeywordSearchField().field("name").label("名称"))
                .addSearchField(new SelectSearchField()
                        .field("createdFrom")
                        .label("注册来源")
                        .multiple(true)
                        .options(User.createFromMap())
                )
                .addSearchField(new DateTimeRangeSearchField()
                        .field("createdRange")
                        .rangeType("daterange")
                        .labelStart("注册开始日期")
                        .labelEnd("注册结束日期")
                        .addShortcut(
                                "近一周",
                                LocalDate.now().minusWeeks(1).toString(),
                                LocalDate.now().toString()
                        )
                )
                .addSearchField(new BrSearchField());
    }

    public Specification<User> getSpecification() {
        // READ https://github.com/wenhao/jpa-spec/blob/master/README_CN.md
        PredicateBuilder<User> predicateBuilder = Specifications.<User>and()
                .like(StringUtils.isNotEmpty(this.name), "name", '%' + this.name + '%')
                .like(StringUtils.isNotEmpty(this.phone), "phone", '%' + this.phone + '%')
                .like(StringUtils.isNotEmpty(this.email), "email", '%' + this.email + '%');

        if (this.createdFrom != null && this.createdFrom.length > 0) {
            PredicateBuilder createdFromPredicateBuilder = Specifications.or();

            for (String createdFrom : this.createdFrom) {
                createdFromPredicateBuilder.eq(StringUtils.isNotEmpty(createdFrom), "createdFrom", createdFrom);
            }

            predicateBuilder.predicate(createdFromPredicateBuilder.build());
        }

        LocalDateTime[] createdRanges = DateTimeUtils.parseSearchDateRange(this.createdRange);

        if (createdRanges != null) {
            predicateBuilder.between("createdAt", createdRanges[0], createdRanges[1]);
        }

        return predicateBuilder.build();
    }
}
