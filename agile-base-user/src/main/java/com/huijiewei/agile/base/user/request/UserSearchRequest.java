package com.huijiewei.agile.base.user.request;

import com.huijiewei.agile.base.request.*;
import com.huijiewei.agile.base.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserSearchRequest extends BaseSearchRequest {
    private String createdFrom;
    private String createdRange;

    public UserSearchRequest() {
        this.addSearchField(new KeywordSearchField().field("phone").label("手机号码"))
                .addSearchField(new KeywordSearchField().field("email").label("邮箱"))
                .addSearchField(new KeywordSearchField().field("name").label("名称"))
                .addSearchField(new SelectSearchField()
                        .field("createdFrom")
                        .label("注册来源")
                        .multiple(true)
                        .options(User.CreatedFromEnums.toMap())
                )
                .addSearchField(new DateRangeSearchField()
                        .field("createdRange")
                        .labelStart("注册开始日期")
                        .labelEnd("注册结束日期")
                )
                .addSearchField(new BrSearchField());
    }
}
