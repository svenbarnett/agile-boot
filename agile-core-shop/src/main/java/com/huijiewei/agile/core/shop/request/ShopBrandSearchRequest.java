package com.huijiewei.agile.core.shop.request;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.huijiewei.agile.core.request.BaseSearchRequest;
import com.huijiewei.agile.core.request.KeywordSearchField;
import com.huijiewei.agile.core.shop.entity.ShopBrand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShopBrandSearchRequest extends BaseSearchRequest {
    private String name;

    public ShopBrandSearchRequest() {
        this
                .addSearchField(new KeywordSearchField().field("name").label("品牌名称"));
    }

    public Specification<ShopBrand> getSpecification() {
        // READ https://github.com/wenhao/jpa-spec/blob/master/README_CN.md
        PredicateBuilder<ShopBrand> predicateBuilder = Specifications.<ShopBrand>and()
                .like(StringUtils.isNotEmpty(this.name), "name", '%' + this.name + '%');

        return predicateBuilder.build();
    }
}
